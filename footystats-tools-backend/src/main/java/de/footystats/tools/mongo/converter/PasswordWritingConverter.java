package de.footystats.tools.mongo.converter;

import de.footystats.tools.services.EncryptionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.util.Base64;


@WritingConverter
@Component
public class PasswordWritingConverter implements Converter<Password, String> {

	private EncryptionService encryptionService;

	public PasswordWritingConverter(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	private String convertToDatabaseColumn(Password attribute) {
		return Base64.getEncoder().encodeToString(encryptionService.encrypt(attribute.getPassword().getBytes()));
	}

	@Override
	public String convert(Password source) {
		return convertToDatabaseColumn(source);
	}
}
