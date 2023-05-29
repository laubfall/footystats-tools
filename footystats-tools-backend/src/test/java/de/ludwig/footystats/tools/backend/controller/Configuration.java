package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({FootystatsProperties.class, DefaultBatchConfiguration.class})
// Excluding classes with TestConfiguration Annotation is necessary cause the component scan also scans
// test packages that includes these classes that would prevent tests from starting / running.
@ComponentScan(value = {"de.ludwig.footystats.tools.backend.services"}, excludeFilters = {@ComponentScan.Filter(TestConfiguration.class)})
@TestConfiguration
public class Configuration {

}
