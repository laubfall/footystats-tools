package de.footystats.tools.services.match;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.jackson.JunitJacksonConfiguration;
import de.footystats.tools.mongo.MongoConfiguration;
import de.footystats.tools.services.EncryptionService;
import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.prediction.PredictionService;
import de.footystats.tools.services.stats.LeagueStatsService;
import de.footystats.tools.services.stats.TeamStatsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Import({JunitJacksonConfiguration.class, FootystatsProperties.class, DomainDataService.class, MongoConfiguration.class, EncryptionService.class})
@TestConfiguration
public class MatchServiceConfiguration {

	@MockBean
	CachedConfiguredStatsService cachedConfiguredStatsService;

	@MockBean
	LeagueStatsService leagueStatsService;

	@MockBean
	TeamStatsService teamStatsService;

	@Bean
	public PredictionService predictionService() {
		return new PredictionService();
	}

	@Bean
	public MatchService matchService(MongoTemplate mongoTemplate, PredictionService predictionService, MappingMongoConverter mappingMongoConverter,
		DomainDataService domainDataService) {
		return new MatchService(mongoTemplate, mappingMongoConverter, predictionService, cachedConfiguredStatsService, leagueStatsService,
			teamStatsService);
	}
}
