package de.footystats.tools.services.domain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
@Component
public class SeasonWritingConverter implements Converter<Season, String> {

	@Override
	public String convert(Season source) {
		return source.toString();
	}

}
