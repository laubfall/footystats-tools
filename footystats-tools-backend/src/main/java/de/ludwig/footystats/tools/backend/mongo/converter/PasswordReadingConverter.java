package de.ludwig.footystats.tools.backend.mongo.converter;

import com.mongodb.DBObject;
import de.ludwig.footystats.tools.backend.services.EncryptionService;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@ReadingConverter
@Component
@Scope("singleton")
public class PasswordReadingConverter implements Converter<DBObject, Password> {

	private final EncryptionService encryptionService;

	public PasswordReadingConverter(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	public Password convertToEntityAttribute(DBObject dbData) {
		return new Password(new String(encryptionService.decrypt(Base64.getDecoder().decode(dbData.get("password").toString()))));
	}

	@Override
	public Password convert(DBObject source) {
		return convertToEntityAttribute(source);
	}
}
