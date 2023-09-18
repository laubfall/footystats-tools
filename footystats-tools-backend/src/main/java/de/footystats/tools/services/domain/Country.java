package de.footystats.tools.services.domain;

import lombok.Getter;

/**
 * Represents a country.
 */
public class Country {

	/**
	 * The country name as used by footystats.org.
	 * <p>
	 * That's the normalized name, no capital letters, no spaces, no special characters.
	 */
	@Getter
	private final String countryNameByFootystats;

	Country(String countryNameByFootystats) {
		this.countryNameByFootystats = countryNameByFootystats;
	}
}
