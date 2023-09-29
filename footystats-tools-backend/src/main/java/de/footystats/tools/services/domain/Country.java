package de.footystats.tools.services.domain;

import lombok.Getter;
import lombok.Value;

/**
 * Represents a country.
 */
@Value
@Getter
public class Country {

	/**
	 * The country name as used by footystats.org.
	 * <p>
	 * That's the normalized name, no capital letters, no spaces, no special characters.
	 */
	String countryNameByFootystats;

	Country(String countryNameByFootystats) {
		this.countryNameByFootystats = countryNameByFootystats;
	}
}
