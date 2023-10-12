package de.footystats.tools.services.stats;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.csv.Configuration;
import de.footystats.tools.services.csv.CsvFileService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@TestConfiguration
@Import({Configuration.class})
public class TeamStatsServiceConfiguration {

	@Bean
	public TeamStatsService teamStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
		CsvFileService<TeamStats> teamStatsCsvFileService, TeamStatsRepository teamStatsRepository, FootystatsProperties footystatsProperties) {
		return new TeamStatsService(mongoTemplate, mappingMongoConverter, teamStatsCsvFileService, teamStatsRepository, footystatsProperties);
	}

	@Bean
	public Team2StatsService team2StatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
		CsvFileService<Team2Stats> team2StatsCsvFileService) {
		return new Team2StatsService(mongoTemplate, mappingMongoConverter, team2StatsCsvFileService);
	}
}
