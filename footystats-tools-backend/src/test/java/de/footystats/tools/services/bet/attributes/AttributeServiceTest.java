package de.footystats.tools.services.bet.attributes;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.jackson.JunitJacksonConfiguration;
import de.footystats.tools.mongo.MongoConfiguration;
import de.footystats.tools.services.prediction.Bet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
@Import({AttributeService.class, JunitJacksonConfiguration.class, FootystatsProperties.class, MongoConfiguration.class, InitialAttributesPopulator.class})
class AttributeServiceTest {
	@Autowired
	private AttributeService attributeService;

	@Test
	void initialize_and_load() {
		List<AttributeSeries> bttsAttributeSeries = attributeService.by(Bet.OVER_ZERO_FIVE);
		Assertions.assertFalse(bttsAttributeSeries.isEmpty());
	}
}
