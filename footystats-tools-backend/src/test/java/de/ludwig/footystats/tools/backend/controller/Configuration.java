package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.mongo.MappingMongoConverterConfiguration;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsServiceConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import({PredictionServiceConfiguration.class, de.ludwig.footystats.tools.backend.services.csv.Configuration.class, FootyStatsCsvUploadController.class, MatchStatsServiceConfiguration.class, MappingMongoConverterConfiguration.class})
@TestConfiguration
public class Configuration {
}
