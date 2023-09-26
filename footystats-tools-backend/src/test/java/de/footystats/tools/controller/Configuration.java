package de.footystats.tools.controller;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.jackson.JunitJacksonConfiguration;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({FootystatsProperties.class, DefaultBatchConfiguration.class, JunitJacksonConfiguration.class})
// Excluding classes with TestConfiguration Annotation is necessary cause the component scan also scans
// test packages that includes these classes that would prevent tests from starting / running.
@ComponentScan(value = {"de.footystats.tools.services"}, excludeFilters = {@ComponentScan.Filter(TestConfiguration.class)})
@TestConfiguration
public class Configuration {

}
