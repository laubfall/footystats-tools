package de.footystats.tools.services.domain;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest(classes = {DomainDataService.class})
class DomainDataServiceTest {

	@Autowired
	private DomainDataService domainDataService;

	@Value("classpath:data/countries.txt")
	private Resource countryResource;

	@Test
	void loadAllCountries() {
		List<Country> countries = domainDataService.getCountries();
		Assertions.assertNotNull(countries);
		Assertions.assertFalse(countries.isEmpty());

		// Adding a new Element has to fail. We assert this now.
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			countries.add(new Country("Somecountry"));
		});
	}

	// Test if every country in countryResource exists in the list of countries and
	// is returned by the countryByName method.
	@Test
	void countryByName() {
		List<String> countryNames = null;
		try {
			countryNames = org.apache.commons.io.IOUtils.readLines(countryResource.getInputStream(), java.nio.charset.StandardCharsets.UTF_8);
		} catch (Exception e) {
			Assertions.fail(e);
		}

		Assertions.assertNotNull(countryNames);
		Assertions.assertFalse(countryNames.isEmpty());

		countryNames.forEach(countryName -> {
			Country country = domainDataService.countryByName(countryName);
			Assertions.assertNotNull(country);
			Assertions.assertEquals(countryName, country.getCountryNameByFootystats());
		});
	}

}
