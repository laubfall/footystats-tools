package de.ludwig.footystats.tools.backend.mongo.converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import de.ludwig.footystats.tools.backend.services.EncryptionService;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.util.Base64;


@WritingConverter
@Component
@Scope("singleton")
public class PasswordWritingConverter implements Converter<Password, DBObject> {

	private EncryptionService encryptionService;

	public PasswordWritingConverter(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	private DBObject convertToDatabaseColumn(Password attribute) {
		var document = new BasicDBObject();
		document.put("password", Base64.getEncoder().encodeToString(encryptionService.encrypt(attribute.getPassword().getBytes())));
		return document;
	}

	@Override
	public DBObject convert(Password source) {
		return convertToDatabaseColumn(source);
	}
}
