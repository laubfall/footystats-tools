package de.footystats.tools.services.prediction.heatmap;

import de.footystats.tools.services.prediction.PredictionAnalyze;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HeatMapService {

	private final MongoTemplate mongoTemplate;
	private StatsBetResultDistributionRepository statsBetResultDistributionRepository;

	public HeatMapService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void trackHeatMapValue(StatsBetResultDistributionKey key, PredictionAnalyze analyzeResult, Object containsStats) {

		if (!PredictionAnalyze.FAILED.equals(analyzeResult) && !PredictionAnalyze.SUCCESS.equals(analyzeResult)) {
			log.info("No heat map calculation because analyze result is not failed or success");
			return;
		}

		log.info("Tracking stats result");

		var incrementUpdate = createIncrementUpdate(analyzeResult);

		heatMapRelevant(containsStats).forEach(statsBetResultDistribution -> {
			//statsBetResultDistribution.setBet(bet);
			var query = Query.query(Criteria.byExample(Example.of(statsBetResultDistribution)));
			mongoTemplate.upsert(query, incrementUpdate, StatsBetResultDistribution.class);
		});
	}

	Collection<StatsBetResultDistribution<?>> heatMapRelevant(Object containsStats) {
		final Collection<StatsBetResultDistribution<?>> statsBetResultDistributions = new HashSet<>();
		final Field[] declaredFields = containsStats.getClass().getDeclaredFields();
		final Stream<Field> heatMappedFields = Arrays.stream(declaredFields).filter(f -> f.getAnnotation(HeatMapped.class) != null);
		heatMappedFields.forEach(f -> {
			try {
				f.setAccessible(true);
				var heatMapAnno = f.getAnnotation(HeatMapped.class);
				final var value = f.get(containsStats);
				statsBetResultDistributions.add(
					StatsBetResultDistribution.builder().statsName(heatMapAnno.heatMappedProperty()).value(value).statsType(containsStats.getClass())
						.build());
			} catch (IllegalAccessException e) {
				log.error("Could not access field", e);
			}
		});

		return statsBetResultDistributions;
	}

	private AggregationUpdate createIncrementUpdate(PredictionAnalyze analyzeResult) {
		var fieldToUpdate = "betSucceeded";
		if (PredictionAnalyze.FAILED.equals(analyzeResult)) {
			fieldToUpdate = "betFailed";
		}
		return AggregationUpdate.update().set(fieldToUpdate).toValue(ArithmeticOperators.valueOf(fieldToUpdate).add(1));
	}
}
