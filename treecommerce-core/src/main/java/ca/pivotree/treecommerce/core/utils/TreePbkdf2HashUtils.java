package ca.pivotree.treecommerce.core.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by cexojo on 07/04/2020
 */

public class TreePbkdf2HashUtils {

	private static final String ERR_MESSAGE_BLANK_PASSWORD = "Password to encode cannot be blank";

	private static final byte[] SALT = "1234".getBytes();
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 512;

	public static String hashPassword(String password) {
		TreeValidationUtils.throwIfBlank(password, ERR_MESSAGE_BLANK_PASSWORD);

		char[] passwordChars = password.toCharArray();

		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(passwordChars, SALT, ITERATIONS, KEY_LENGTH);
			SecretKey key = skf.generateSecret(spec);
			byte[] res = key.getEncoded();
			return Hex.encodeHexString(res);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
}
