package cloud.pangeacyber.pangea;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class Utils {

	public static String stringToStringB64(String data) {
		return new String(Base64.encodeBase64(data.getBytes()));
	}

	public static String stringB64toString(String dataB64) {
		return new String(Base64.decodeBase64(dataB64.getBytes()));
	}

	public static String hashSHA256(String data) {
		return DigestUtils.sha256Hex(data);
	}

	public static String hashSHA1(String data) {
		return DigestUtils.sha1Hex(data);
	}

	public static String getHashPrefix(String hash, int len) {
		return hash.substring(0, len);
	}
}
