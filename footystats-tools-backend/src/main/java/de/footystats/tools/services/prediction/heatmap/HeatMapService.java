package de.footystats.tools.services.prediction.heatmap;

import de.footystats.tools.services.prediction.PredictionAnalyze;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
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
	 * Track a stats value in a StatsBetResultDistribution entity for the given key.
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
			log.info("No heat map calculation because analyze result is not failed or success");
			return;
		}

		log.info("Tracking stats result");

		var incrementUpdate = createIncrementUpdate(analyzeResult);

		heatMapRelevant(containsStats).stream().map(s -> applyKey(s, key)).forEach(statsBetResultDistribution -> {
			var query = Query.query(Criteria.byExample(Example.of(statsBetResultDistribution)));
			mongoTemplate.upsert(query, incrementUpdate, StatsBetResultDistribution.class);
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
		Criteria keyCriteria = Criteria.where("key.bet").is(key.getBet()).andOperator(
			Criteria.where("key.country").is(key.getCountry()),
			Criteria.where("key.league").is(key.getLeague()),
			Criteria.where("key.season").is(key.getSeason()),
			Criteria.where("statsName").is(statsName),
			Criteria.where("value").is(value)
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
				if (value != null) {
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
