package de.footystats.tools.services.domain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@ReadingConverter
@Component
public class SeasonReadingConverter implements Converter<String, Season> {

	@Override
	public Season convert(String source) {
		return new Season(source);
	}
}
