package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.mongo.MappingMongoConverterConfiguration;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.stats.LeagueStatsServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.stats.TeamStatsServiceConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import({PredictionServiceConfiguration.class, de.ludwig.footystats.tools.backend.services.csv.Configuration.class,
	FootyStatsCsvUploadController.class, MatchStatsServiceConfiguration.class, LeagueStatsServiceConfiguration.class,
	TeamStatsServiceConfiguration.class, MappingMongoConverterConfiguration.class})
@TestConfiguration
public class Configuration {
}
