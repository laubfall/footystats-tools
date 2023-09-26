package de.footystats.tools.jackson;

import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Test configuration for JsonTests. Imports the necessary JsonComponentModule.
 */
@Import({JsonComponentModule.class})
@TestConfiguration
public class JunitJacksonConfiguration extends JackonConfiguration {

}
