package de.ludwig.footystats.tools.backend.services.stats;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Import({FootystatsProperties.class})
@TestConfiguration
public class TeamStatsServiceConfiguration {

	@Bean
	public CsvFileService<TeamStats> teamStatsCsvFileService(){
		return new CsvFileService<>();
	}

	@Bean
	public TeamStatsService teamStatsService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, CsvFileService<TeamStats> teamStatsCsvFileService, TeamStatsRepository teamStatsRepository, FootystatsProperties footystatsProperties) {
		return new TeamStatsService(mongoTemplate, mappingMongoConverter, teamStatsCsvFileService, teamStatsRepository, footystatsProperties);
	}
}
