package de.footystats.tools.services.domain;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Service for domain data like country and league names.
 */
@Slf4j
@Service
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
	 * @param name the name of the country as defined in countries.txt
	 * @return the country with the given name
	 */
	public Country countryByName(String name) {
		return countries.stream().filter(c -> c.getCountryNameByFootystats().equals(name)).findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No country with name " + name + " exists."));
	}

	/**
	 * Returns the country with the given name. Throw an IllegalArgumentException if no country with the given name exists. The name is normalized
	 * before the lookup that means it is converted to lowercase and whitespaces are replaced by dashes. The normalized name is the name used in
	 * countries.txt.
	 *
	 * @param name the name of the country. Either the name as defined in countries.txt or more human-readable english name (e.g. "Germany", "New
	 *             Zealand")
	 * @return the country with the given name
	 */
	public Country countryByNormalizedName(String name) {
		var normalized = name.toLowerCase().trim();
		normalized = StringUtils.replace(normalized, " ", "-");
		normalized = StringUtils.remove(normalized, "."); // e.g. St. Kitts and Nevis -> st-kitts-and-nevis
		return countryByName(normalized);
	}

	@PostConstruct
	private void init() throws IOException {
		// countryReference is a textfile with a list of countries.
		// Create a Country object for each country in the file.
		IOUtils.readLines(countryResource.getInputStream(), StandardCharsets.UTF_8).forEach(line -> {
			var country = new Country(line);
			log.debug("Created country: {}", country);
			countries.add(country);
		});
	}
}
