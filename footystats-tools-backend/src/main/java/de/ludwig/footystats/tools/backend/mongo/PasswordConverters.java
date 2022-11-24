package de.ludwig.footystats.tools.backend.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import de.ludwig.footystats.tools.backend.FootystatsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;


@Component
@Scope("singleton")
public class PasswordConverters {

	private final FootystatsProperties properties;

	public PasswordConverters(FootystatsProperties properties) {
		this.properties = properties;
	}

	private static class Helper {
		static final String AES = "AES";
		static final String SECRET = "secret-key-12345";

		final Key key;
		final Cipher cipher;

		public Helper() {

			key = new SecretKeySpec(SECRET.getBytes(), AES);
			try {
				cipher = Cipher.getInstance(AES);
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static Helper helper = new Helper();

	@WritingConverter
	public enum PasswordWritingConverter implements Converter<Password, DBObject> {
		INSTANCE,
		;

		Helper helper = PasswordConverters.helper;

		private DBObject convertToDatabaseColumn(Password attribute) {
			try {
				helper.cipher.init(Cipher.ENCRYPT_MODE, helper.key);
				var document = new BasicDBObject();
				document.put("password", Base64.getEncoder().encodeToString(helper.cipher.doFinal(attribute.getPassword().getBytes())));
				return document;
			} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
				throw new IllegalStateException(e);
			}
		}

		@Override
		public DBObject convert(Password source) {
			return convertToDatabaseColumn(source);
		}
	}

	@ReadingConverter
	public enum PasswordReadingConverter implements Converter<Password, String> {
		INSTANCE,
		;
		Helper helper = PasswordConverters.helper;

		public String convertToEntityAttribute(Password dbData) {
			try {
				helper.cipher.init(Cipher.DECRYPT_MODE, helper.key);
				return new String(helper.cipher.doFinal(Base64.getDecoder().decode(dbData.getPassword())));
			} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
				throw new IllegalStateException(e);
			}
		}

		@Override
		public String convert(Password source) {
			return convertToEntityAttribute(source);
		}
	}

	public static List<Converter<?, ?>> converters() {
		return List.of(PasswordWritingConverter.INSTANCE, PasswordReadingConverter.INSTANCE);
	}
}
