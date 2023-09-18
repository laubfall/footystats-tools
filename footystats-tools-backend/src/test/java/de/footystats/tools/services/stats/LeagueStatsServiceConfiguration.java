package de.footystats.tools.services.stats;

import de.footystats.tools.services.csv.CsvFileService;
import de.footystats.tools.services.domain.DomainDataService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@TestConfiguration
public class LeagueStatsServiceConfiguration {

	@Bean
	public LeagueStatsService leagueStatsService(MongoTemplate template, MappingMongoConverter mappingMongoConverter,
		CsvFileService<LeagueStats> leagueStatsService, DomainDataService domainDataService) {
		return new LeagueStatsService(template, mappingMongoConverter, leagueStatsService, domainDataService);
	}
}
