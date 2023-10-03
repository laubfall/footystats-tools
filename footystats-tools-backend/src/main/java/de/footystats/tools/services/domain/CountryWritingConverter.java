package de.footystats.tools.services.domain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

/**
 * Converts a country object to a country name for storing in mongodb.
 */
@WritingConverter
@Component
public class CountryWritingConverter implements Converter<Country, String> {

	@Override
	public String convert(Country source) {
		return source.getCountryNameByFootystats();
	}
}
