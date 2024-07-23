package cloud.pangeacyber.pangea.audit.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class Hash {

	public static String hash(String data) {
		return DigestUtils.sha256Hex(data);
	}

	public static byte[] unhexlify(String hex) {
		try {
			return Hex.decodeHex(hex);
		} catch (DecoderException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public static byte[] decode(String hash) {
		return unhexlify(hash);
	}

	public static byte[] hash(byte[] h1, byte[] h2) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(h1);
		outputStream.write(h2);
		byte data[] = outputStream.toByteArray();
		return DigestUtils.sha256(data);
	}
}
