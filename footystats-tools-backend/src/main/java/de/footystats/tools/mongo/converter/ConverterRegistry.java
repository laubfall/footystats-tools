package de.footystats.tools.mongo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("singleton")
public class ConverterRegistry {

	private PasswordReadingConverter passwordReadingConverter;

	private PasswordWritingConverter passwordWritingConverter;

	public List<Converter<?, ?>> converters() {
		return List.of(passwordReadingConverter, passwordWritingConverter);
	}

	@Autowired
	public void setPasswordWritingConverter(PasswordWritingConverter passwordWritingConverter) {
		this.passwordWritingConverter = passwordWritingConverter;
	}

	@Autowired
	public void setPasswordReadingConverter(PasswordReadingConverter passwordReadingConverter) {
		this.passwordReadingConverter = passwordReadingConverter;
	}
}
