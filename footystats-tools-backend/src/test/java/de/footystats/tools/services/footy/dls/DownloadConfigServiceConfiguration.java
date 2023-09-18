package de.footystats.tools.services.footy.dls;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.csv.CsvFileService;
import de.footystats.tools.services.domain.DomainDataService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Import({FootystatsProperties.class, DomainDataService.class})
@TestConfiguration
public class DownloadConfigServiceConfiguration {

	@Bean
	public DownloadConfigService downloadConfigService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter,
		CsvFileService<DownloadCountryLeagueStatsCsvEntry> csvFileService) {
		return new DownloadConfigService(mongoTemplate, mappingMongoConverter, csvFileService);
	}

	@Bean
	public CsvFileService<DownloadCountryLeagueStatsCsvEntry> csvFileService(DomainDataService domainDataService) {
		return new CsvFileService<>(domainDataService);
	}
}
