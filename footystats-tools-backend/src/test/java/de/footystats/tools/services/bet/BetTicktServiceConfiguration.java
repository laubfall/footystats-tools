package de.footystats.tools.services.bet;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.jackson.JunitJacksonConfiguration;
import de.footystats.tools.mongo.MongoConfiguration;
import de.footystats.tools.services.bet.attributes.AttributeSeriesServiceConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import({BetTicketService.class, JunitJacksonConfiguration.class, FootystatsProperties.class, MongoConfiguration.class, AttributeSeriesServiceConfiguration.class})
@TestConfiguration
public class BetTicktServiceConfiguration {
}
