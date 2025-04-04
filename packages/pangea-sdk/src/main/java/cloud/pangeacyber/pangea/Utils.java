package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.zip.CRC32C;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.util.encoders.Hex;

public final class Utils {

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
		byte[] unicodePasswordBytes = data.getBytes(StandardCharsets.UTF_16LE);
		final var md4 = new MD4Digest();
		md4.update(unicodePasswordBytes, 0, unicodePasswordBytes.length);
		final var ntlmHash = new byte[md4.getDigestSize()];
		md4.doFinal(ntlmHash, 0);
		return Hex.toHexString(ntlmHash).toUpperCase(Locale.ROOT);
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

	public static FileParams getFileUploadParams(File file) throws PangeaException {
		String crc, sha256;
		int size = 0;
		CRC32C crc32c = new CRC32C();
		byte[] buffer = new byte[8192]; // Buffer size can be adjusted
		int bytesRead;

		try (InputStream inputStream = new FileInputStream(file)) {
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				size += bytesRead;
				crc32c.update(buffer, 0, bytesRead);
			}

			// Pad to 8 characters to satisfy Secure Share's input validation.
			crc = String.format("%08x", crc32c.getValue()).toLowerCase(Locale.ROOT);
		} catch (IOException e) {
			throw new PangeaException(String.format("Failed to read file: %s", file.getAbsolutePath()), e);
		}

		try (InputStream inputStream = new FileInputStream(file)) {
			sha256 = DigestUtils.sha256Hex(inputStream);
		} catch (IOException e) {
			throw new PangeaException(String.format("Failed to read file: %s", file.getAbsolutePath()), e);
		}
		return new FileParams(size, sha256, crc);
	}

	public static long getFileSize(File file) {
		return file.length();
	}

	public static FileParams getFileUploadParams(String filepath) throws PangeaException {
		File file = null;
		try {
			file = new File(filepath);
			return Utils.getFileUploadParams(file);
		} catch (NullPointerException e) {
			throw new PangeaException(String.format("Failed to read file due to null pathname"), e);
		}
	}
}
