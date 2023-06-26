package support;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.services.EncryptionService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

/**
 * Test configuration in case you want to include the mongo converters in your tests.
 *
 * For example: make sure Password-Type is encrypted in tests like it is in the application.
 */
@TestConfiguration
@ComponentScans({
	@ComponentScan(value = {"de.footystats.tools.mongo.converter"})
})
@Import({FootystatsProperties.class, EncryptionService.class})
public class MongoMappingConverterConfiguration {
}
