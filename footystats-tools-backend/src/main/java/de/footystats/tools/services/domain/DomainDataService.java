package de.footystats.tools.services.domain;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Service for domain data like country and league names.
 */
@Service
@Slf4j
public class DomainDataService {

	private final List<Country> countries = new ArrayList<>();

	@Value("classpath:data/countries.txt")
	private Resource countryResource;

	/**
	 * Returns an unmodifiable list of all countries.
	 */
	public List<Country> getCountries() {
		return List.copyOf(countries);
	}

	/**
	 * Returns the country with the given name. Throw an IllegalArgumentException if no country with the given name exists.
	 *
	 * @param name the name of the country
	 * @return the country with the given name
	 */
	public Country countryByName(String name) {
		return countries.stream().filter(c -> c.getCountryNameByFootystats().equals(name)).findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No country with name " + name + " exists."));
	}

	@PostConstruct
	private void init() throws IOException {
		// countryReference is a textfile with a list of countries.
		// Create a Country object for each country in the file.
		IOUtils.readLines(countryResource.getInputStream(), StandardCharsets.UTF_8).forEach(line -> {
			var country = new Country(line);
			log.info("Created country: {}", country);
			countries.add(country);
		});
	}
}
