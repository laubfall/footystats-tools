package de.footystats.tools.services.stats.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.footystats.tools.services.domain.Country;
import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.stats.LeagueStats;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ComponentScan(basePackages = {"de.footystats.tools.services.stats.jackson"})
@ContextConfiguration(classes = {DomainDataService.class})
@JsonTest
class CountrySerializerTest {

	@Autowired
	private DomainDataService domainDataService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void serialize() throws IOException {
		Country germany = domainDataService.countryByName("germany");
		Assertions.assertNotNull(germany);

		StringWriter stringWriter = new StringWriter();
		var leagueStats = new LeagueStats();
		leagueStats.setCountry(germany);
		objectMapper.writeValue(stringWriter, leagueStats);
		// Check the content of stringWriter if it cotains the country property as a single field.
		Assertions.assertTrue(stringWriter.toString().contains("\"country\":\"germany\""));
	}
}
