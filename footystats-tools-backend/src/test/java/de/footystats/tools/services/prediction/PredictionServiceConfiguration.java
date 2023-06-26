package de.footystats.tools.services.prediction;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import de.footystats.tools.services.stats.MatchStatsServiceConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Import({FootystatsProperties.class, MatchStatsServiceConfiguration.class})
@TestConfiguration
public class PredictionServiceConfiguration {
	@Bean
	public PredictionQualityService predictionQualityService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, BetPredictionQualityRepository betPredictionAggregateRepository) {
		return new PredictionQualityService(mongoTemplate, mappingMongoConverter, betPredictionAggregateRepository);
	}
}
