package de.footystats.tools.mongo.converter;

import de.footystats.tools.services.EncryptionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@ReadingConverter
@Component
public class PasswordReadingConverter implements Converter<String, Password> {

	private final EncryptionService encryptionService;

	public PasswordReadingConverter(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	public Password convertToEntityAttribute(String dbData) {
		return new Password(new String(encryptionService.decrypt(Base64.getDecoder().decode(dbData))));
	}

	@Override
	public Password convert(String source) {
		return convertToEntityAttribute(source);
	}
}
