package de.footystats.tools.services.domain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
@Component
public class CountryWritingConverter implements Converter<Country, String> {

	@Override
	public String convert(Country source) {
		return source.getCountryNameByFootystats();
	}
}
