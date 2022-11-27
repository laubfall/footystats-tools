package de.ludwig.footystats.tools.backend.services;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Service
public class EncryptionService {
	private static final String AES = "AES";
	private final Key key;
	private final Cipher encode;
	private final Cipher decode;

	public EncryptionService(FootystatsProperties properties) {
		key = new SecretKeySpec(properties.getEncryptionSecret().getBytes(), AES);
		try {
			encode = Cipher.getInstance(AES);
			decode = Cipher.getInstance(AES);
			encode.init(Cipher.ENCRYPT_MODE, key);
			decode.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] encrypt(final byte[] data){
		try {
			return encode.doFinal(data);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] decrypt(final byte[] data){
		try {
			return decode.doFinal(data);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}
}
