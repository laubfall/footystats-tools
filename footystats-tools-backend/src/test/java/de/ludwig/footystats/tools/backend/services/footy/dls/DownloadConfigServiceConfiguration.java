package de.ludwig.footystats.tools.backend.services.footy.dls;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.csv.CsvFileService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Import({FootystatsProperties.class})
@TestConfiguration
public class DownloadConfigServiceConfiguration {
	@Bean
	public DownloadConfigService downloadConfigService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter, CsvFileService<DownloadCountryLeagueStatsCsvEntry> csvFileService) {
		return new DownloadConfigService(mongoTemplate, mappingMongoConverter, csvFileService);
	}

	@Bean
	public CsvFileService<DownloadCountryLeagueStatsCsvEntry> csvFileService() {
		return new CsvFileService<>();
	}
}
