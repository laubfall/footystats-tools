package de.footystats.tools.services.csv;

import com.opencsv.bean.AbstractBeanField;
import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.EncryptionService;
import de.footystats.tools.services.domain.DomainDataService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({FootystatsProperties.class, EncryptionService.class, CsvFileService.class, AutowireCapableHeaderNameStrategy.class, DomainDataService.class})
// Scan for all Csv-Converters to inject them into the spring dependency container.
@ComponentScan(includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AbstractBeanField.class)}, basePackages = {
	// UseDefaultFilters=false is necessary to exclude every other classes
	// then the ones specified in includeFilters.
	"de.footystats.tools.services"}, useDefaultFilters = false)
public class Configuration {

}
