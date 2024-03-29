package de.footystats.tools.services.stats;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.match.MatchService;
import de.footystats.tools.services.match.MatchServiceConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * Test configuration for JsonTests. Actually empty but required to avoid using the SpringBoot Config.
 */
@Import({MatchServiceConfiguration.class})
@TestConfiguration
public class MatchStatsServiceConfiguration {

	@Bean
	public MatchStatsService matchStatsService(MongoTemplate template, MappingMongoConverter mappingMongoConverter, MatchService matchService,
		FootystatsProperties fsProperties, MatchStatsRepository matchStatsRepository, DomainDataService domainDataService) {
		return new MatchStatsService(template, mappingMongoConverter, fsProperties, matchService, matchStatsRepository, domainDataService);
	}
}
