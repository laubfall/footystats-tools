package de.footystats.tools.services.stats;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.jackson.JackonsConfiguration;
import de.footystats.tools.services.match.CachedConfiguredStatsService;
import de.footystats.tools.services.match.MatchService;
import de.footystats.tools.services.prediction.PredictionService;
import de.footystats.tools.services.stats.batch.IMatchStatsJobService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * Test configuration for JsonTests. Actually empty but required to avoid using the SpringBoot Config.
 */
@Import({JackonsConfiguration.class, FootystatsProperties.class})
@TestConfiguration
public class MatchStatsServiceConfiguration {

	@MockBean
	IMatchStatsJobService matchStatsJobService;

	@MockBean
	CachedConfiguredStatsService cachedConfiguredStatsService;

	@Bean
	public PredictionService predictionService() {
		return new PredictionService();
	}

	@Bean
	public MatchService matchService(MongoTemplate mongoTemplate, PredictionService predictionService, MappingMongoConverter mappingMongoConverter) {
		return new MatchService(mongoTemplate, mappingMongoConverter, predictionService, cachedConfiguredStatsService);
	}

	@Bean
	public MatchStatsService matchStatsService(MongoTemplate template, MappingMongoConverter mappingMongoConverter, MatchService matchService,
		FootystatsProperties fsProperties, MatchStatsRepository matchStatsRepository, IMatchStatsJobService matchStatsJobService) {
		return new MatchStatsService(template, mappingMongoConverter, fsProperties, matchService, matchStatsRepository, matchStatsJobService);
	}
}
