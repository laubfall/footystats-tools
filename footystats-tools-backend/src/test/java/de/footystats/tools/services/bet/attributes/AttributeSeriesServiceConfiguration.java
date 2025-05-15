package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.jackson.JunitJacksonConfiguration;
import de.footystats.tools.mongo.MongoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@EnableAspectJAutoProxy
@Import({AttributeSeriesService.class, AttributeSeriesRepositoryAspect.class, JunitJacksonConfiguration.class, FootystatsProperties.class, MongoConfiguration.class, InitialAttributesPopulator.class})
public class AttributeSeriesServiceConfiguration {
}
