package cloud.pangeacyber.pangea;

import org.apache.commons.codec.binary.Base64;

public class Utils {

	public static String stringToStringB64(String data) {
		return new String(Base64.encodeBase64(data.getBytes()));
	}

	public static String stringB64toString(String dataB64) {
		return new String(Base64.decodeBase64(dataB64.getBytes()));
	}
}
