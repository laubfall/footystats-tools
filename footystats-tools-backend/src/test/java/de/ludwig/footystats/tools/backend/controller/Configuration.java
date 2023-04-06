package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.mongo.converter.ConverterRegistry;
import de.ludwig.footystats.tools.backend.mongo.converter.MappingMongoConverterConfiguration;
import de.ludwig.footystats.tools.backend.services.EncryptionService;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadConfigService;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.prediction.outcome.StatisticalResultOutcome;
import de.ludwig.footystats.tools.backend.services.prediction.outcome.StatisticalResultOutcomeService;
import de.ludwig.footystats.tools.backend.services.stats.LeagueStatsServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.stats.TeamStatsServiceConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import({PredictionServiceConfiguration.class, de.ludwig.footystats.tools.backend.services.csv.Configuration.class,
	FootyStatsCsvUploadController.class, MatchStatsServiceConfiguration.class, LeagueStatsServiceConfiguration.class,
	TeamStatsServiceConfiguration.class, MappingMongoConverterConfiguration.class, ConverterRegistry.class,  EncryptionService.class, CsvFileDownloadService.class, DownloadConfigService.class, StatisticalResultOutcomeService.class})
@TestConfiguration
public class Configuration {
}
