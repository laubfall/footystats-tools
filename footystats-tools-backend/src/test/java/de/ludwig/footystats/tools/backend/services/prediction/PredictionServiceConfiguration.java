package de.ludwig.footystats.tools.backend.services.prediction;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsServiceConfiguration;
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
