package de.footystats.tools.services.heatmap;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import de.footystats.tools.services.prediction.PredictionAnalyze;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Creates "heat maps" of stats values that occurred in successful and failed bets.
 * <p>
 * Heat maps can be used to analyze which stats values are good indicators for successful bets.
 * <p>
 * Service makes it possible to create heat maps on "different levels" of stats entities. Levels can be: Bet, Bet-Country, Bet-Country-League and
 * Bet-Country-League-Season.
 */
@Slf4j
@Service
public class HeatMapService {

	private final MongoTemplate mongoTemplate;

	public HeatMapService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Track a stats value in a StatsBetResultDistribution entity for the given key and its broader versions (e.g. Bet-Country-League, Bet-Country,
	 * Bet).
	 * <p>
	 * Method takes care of incrementing the "betSucceeded" or "betFailed" field of the StatsBetResultDistribution entity in case a
	 * StatsBetResultDistribution entity with the given key, stats value and stats name already exists.
	 *
	 * @param key           Mandatory key.
	 * @param analyzeResult Mandatory analyze result.
	 * @param containsStats Mandatory object that contains stats values (for example a MatchStats entity).
	 */
	public void trackHeatMapValue(StatsBetResultDistributionKey key, PredictionAnalyze analyzeResult, Object containsStats) {
		if (!PredictionAnalyze.FAILED.equals(analyzeResult) && !PredictionAnalyze.SUCCESS.equals(analyzeResult)) {
			log.info("No heat map calculation because analyze result is not equals failed or success");
			return;
		}

		log.info("Tracking stats result");

		var incrementUpdate = createIncrementUpdate(analyzeResult);

		heatMapRelevant(containsStats).forEach(statsBetResultDistribution -> {
			var actualKey = key;
			var upsertBroadest = false;
			do {
				upsertBroadest = actualKey.broadest();
				statsBetResultDistribution.setKey(actualKey);
				final var query = Query.query(
					Criteria.byExample(Example.of(statsBetResultDistribution, ExampleMatcher.matching().withIncludeNullValues())));
				final var upsertResult = mongoTemplate.upsert(query, incrementUpdate, StatsBetResultDistribution.class);
				if (log.isTraceEnabled()) {
					log.trace("Upserted heatmap value: {}", upsertResult);
				}
				if (upsertBroadest) {
					break;
				}
				actualKey = actualKey.broader();
			} while (true);
		});
	}

	/**
	 * Find a stats bet result distribution by key, stats value and stats name.
	 *
	 * @param key       Mandatory key.
	 * @param statsName Mandatory stats name.
	 * @param value     Mandatory stats value.
	 * @param <S>       Type of stats bet result distribution.
	 * @return Optional of stats bet result distribution.
	 */
	public <S> Optional<S> findByKey(StatsBetResultDistributionKey key, String statsName, Object value) {
		Criteria keyCriteria = where("key").is(key)
			.andOperator(
				where("statsName").is(statsName),
				where("value").is(value)
			);

		return Optional.ofNullable((S) mongoTemplate.findOne(Query.query(keyCriteria), StatsBetResultDistribution.class));
	}

	private StatsBetResultDistribution<?> applyKey(StatsBetResultDistribution<?> statsBetResultDistribution, StatsBetResultDistributionKey key) {
		statsBetResultDistribution.setKey(key);
		return statsBetResultDistribution;
	}

	Collection<StatsBetResultDistribution<?>> heatMapRelevant(Object containsStats) {
		final Collection<StatsBetResultDistribution<?>> statsBetResultDistributions = new HashSet<>();
		final Field[] declaredFields = containsStats.getClass().getDeclaredFields();
		final Stream<Field> heatMappedFields = Arrays.stream(declaredFields).filter(f -> f.getAnnotation(HeatMap.class) != null);
		heatMappedFields.forEach(f -> {
			try {
				f.setAccessible(true);
				var heatMapAnno = f.getAnnotation(HeatMap.class);
				final var value = f.get(containsStats);
				if (!ignore(value, heatMapAnno)) {
					statsBetResultDistributions.add(
						createStatsBetResultDistribution(buildStatsName(heatMapAnno, f), applyFraction(value, heatMapAnno)));
				}
			} catch (IllegalAccessException e) {
				log.error("Could not access field", e);
			}
		});

		return statsBetResultDistributions;
	}

	private String buildStatsName(HeatMap heatMapAnno, Field f) {
		return heatMapAnno.heatMappedProperty().isEmpty() ? f.getName() : heatMapAnno.heatMappedProperty();
	}

	private boolean ignore(Object value, HeatMap heatMapAnno) {
		if (value == null) {
			return true;
		}

		if (value instanceof Number num) {
			var statsValue = num.doubleValue();
			return statsValue < heatMapAnno.ignoreLt() || statsValue > heatMapAnno.ignoreGt();
		}

		return false;
	}

	private Object applyFraction(Object value, HeatMap heatMapAnno) {
		var operator = Math.pow(10, heatMapAnno.fraction());

		return switch (value) {
			case Integer v -> v;
			case Long l -> l;
			case Double d -> Math.floor(d * operator) / operator;
			case Float f -> Math.floor(f * operator) / operator;
			default -> throw new IllegalStateException("Unexpected value type for statsBetResultDistribution entity: " + value);
		};
	}

	private <V> StatsBetResultDistribution<?> createStatsBetResultDistribution(String statsName,
		V value) {
		return switch (value) {
			case Integer v -> IntegerStatsDistribution.builder().statsName(statsName).value(v).build();
			case Double d -> DoubleStatsDistribution.builder().statsName(statsName).value(d).build();
			case Float f -> FloatStatsDistribution.builder().statsName(statsName).value(f).build();
			default -> throw new IllegalStateException("Unexpected value type for statsBetResultDistribution entity: " + value);
		};
	}

	private Update createIncrementUpdate(PredictionAnalyze analyzeResult) {
		var fieldToUpdate = "betSucceeded";
		if (PredictionAnalyze.FAILED.equals(analyzeResult)) {
			fieldToUpdate = "betFailed";
		}
		return new Update().inc(fieldToUpdate, 1);
	}
}
