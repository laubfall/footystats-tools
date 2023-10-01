package de.footystats.tools.services.csv;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.EncryptionService;
import de.footystats.tools.services.domain.DomainDataService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({FootystatsProperties.class, EncryptionService.class, CsvFileService.class, AutowireCapableHeaderNameStrategy.class, DomainDataService.class})
public class Configuration {

}
