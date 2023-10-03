package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.FileHashReputationBulkRequest;
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

	public FileIntelClient(Builder builder) {
		super(builder, serviceName);
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
	 * Reputation
	 * @pangea.description Retrieve a reputation score for a file hash from a provider, including an optional detailed report.
	 * @pangea.operationId file_intel_post_v1_reputation
	 * @param request
	 * @return FileReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FileHashReputationRequest request = new FileHashReputationRequest
	 * 	.Builder(
	 * 		"142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
	 * 		"sha256")
	 * 	.provider("reversinglabs")
	 * 	.verbose(false)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * FileReputationResponse response = client.reputation(request);
	 * }
	 */
	public FileReputationResponse reputation(FileHashReputationRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/reputation", request, FileReputationResponse.class);
	}

	/**
	 * Reputation
	 * @pangea.description Retrieve a reputation score for a file hash list from a provider, including an optional detailed report.
	 * @pangea.operationId FIXME:
	 * @param request
	 * @return FileReputationBulkResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FileHashReputationRequest request = new FileHashReputationBulkRequest
	 * 	.Builder(
	 * 		"142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", FIXME:
	 * 		"sha256")
	 * 	.provider("reversinglabs")
	 * 	.verbose(false)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * FileReputationResponse response = client.reputation(request);
	 * }
	 */
	public FileReputationBulkResponse reputationBulk(FileHashReputationBulkRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/reputation", request, FileReputationBulkResponse.class);
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
