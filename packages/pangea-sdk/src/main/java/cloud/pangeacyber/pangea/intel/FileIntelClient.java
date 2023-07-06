package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.FileHashReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

public class FileIntelClient extends BaseClient {

	public static final String serviceName = "file-intel";
	private static final boolean supportMultiConfig = false;

	public FileIntelClient(Builder builder) {
		super(builder, serviceName, supportMultiConfig);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public FileIntelClient build() {
			return new FileIntelClient(this);
		}
	}

	/**
	 * Reputation check - hash, hashType, provider, verbose, raw
	 * @pangea.description Retrieve file reputation from a provider, using the file's hash.
	 * @param hash hash of the file
	 * @param hashType Type of hash, can be "sha256", "sha1" or "md5"
	 * @param provider provider to get reputation from
	 * @param verbose select a more verbose response
	 * @param raw if true response include provider raw response. This should vary from one provider to another one.
	 * @return FileReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
		// FIXME:
	 */
	public FileReputationResponse reputation(FileHashReputationRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/reputation", request, FileReputationResponse.class);
	}

	public static String calculateSHA256fromFile(String filepath) throws PangeaException {
		byte[] buffer = new byte[8192];
		int count;
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new PangeaException("NoSuchAlgorithm", e);
		}

		FileInputStream file = null;
		try {
			file = new FileInputStream(filepath);
		} catch (FileNotFoundException e) {
			throw new PangeaException("FileNotFoundException", e);
		}

		BufferedInputStream bis = new BufferedInputStream(file);

		try {
			while ((count = bis.read(buffer)) > 0) {
				digest.update(buffer, 0, count);
			}
			bis.close();
		} catch (IOException e) {
			throw new PangeaException("Failed to update digest", e);
		}

		byte[] hash = digest.digest();
		return Hex.encodeHexString(hash);
	}
}
