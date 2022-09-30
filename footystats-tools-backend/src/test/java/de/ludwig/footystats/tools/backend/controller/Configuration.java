package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.mongo.MappingMongoConverterConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import({de.ludwig.footystats.tools.backend.services.csv.Configuration.class, FootyStatsCsvUploadController.class, de.ludwig.footystats.tools.backend.services.stats.Configuration.class, MappingMongoConverterConfiguration.class})
@TestConfiguration
public class Configuration {
}
