package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@TestConfiguration
public class LeagueStatsServiceConfiguration {
	@Bean
	public LeagueStatsService leagueStatsService(MongoTemplate template, MappingMongoConverter mappingMongoConverter, CsvFileService<LeagueStats> leagueStatsService){
		return new LeagueStatsService(template, mappingMongoConverter, leagueStatsService);
	}
}
