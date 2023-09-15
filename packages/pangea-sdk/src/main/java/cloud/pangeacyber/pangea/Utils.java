package cloud.pangeacyber.pangea;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import jcifs.util.Hexdump;
import jcifs.util.MD4;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
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

	public static String hashSHA512(String data) {
		return DigestUtils.sha512Hex(data);
	}

	public static String hashNTLM(String data) {
		try {
			byte[] unicodePasswordBytes = data.getBytes("UTF-16LE");
			MD4 md4 = new jcifs.util.MD4();
			byte[] ntlmHash = md4.digest(unicodePasswordBytes);
			return Hexdump.toHexString(ntlmHash, 0, ntlmHash.length * 2);
		} catch (Exception e) {
			return "";
		}
	}

	public static String hashSHA256fromFilepath(String filepath) throws IOException, FileNotFoundException {
		return DigestUtils.sha256Hex(new FileInputStream(filepath));
	}

	public static String hashSHA1fromFilepath(String filepath) throws IOException, FileNotFoundException {
		return DigestUtils.sha1Hex(new FileInputStream(filepath));
	}

	public static String getHashPrefix(String hash, int len) {
		return hash.substring(0, len);
	}
}
