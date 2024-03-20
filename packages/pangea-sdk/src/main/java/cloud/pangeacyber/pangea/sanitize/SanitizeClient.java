package cloud.pangeacyber.pangea.sanitize;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.AcceptedResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import cloud.pangeacyber.pangea.sanitize.requests.*;
import cloud.pangeacyber.pangea.sanitize.responses.SanitizeResponse;
import java.io.File;

/** Sanitize client. */
public class SanitizeClient extends BaseClient {
	/** Service name. */
	public static String serviceName = "sanitize";

	/**
	 * Create a new Sanitize client using the given builder.
	 *
	 * @param builder Sanitize client builder.
	 */
	public SanitizeClient(Builder builder) {
		super(builder, serviceName);
	}

	/** Sanitize client builder. */
	public static class Builder extends BaseClient.Builder<Builder> {
		/**
		 * Constructor.
		 *
		 * @param config Configuration.
		 */
		public Builder(Config config) {
			super(config);
		}

		/** Build a Sanitize client. */
		public SanitizeClient build() {
			return new SanitizeClient(this);
		}
	}

	/**
	 * Sanitize
	 * @pangea.description Apply file sanitization actions according to specified rules. Beta API.
	 * @pangea.operationId sanitize_post_v1beta_sanitize
	 * @param request Request parameters.
	 * @param file File to sanitize.
	 * @return The sanitized file and information on the sanitization that was performed.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var file = new File("/path/to/file.pdf");
	 * var response = client.sanitize(
	 *     new SanitizeRequest.Builder().uploadedFileName("uploaded_file").build(),
	 *     file
	 * );
	 * }
	 */
	public SanitizeResponse sanitize(SanitizeRequest request, File file) throws PangeaException, PangeaAPIException {
		TransferMethod tm = request.getTransferMethod();
		String name;

		if (tm == TransferMethod.PUT_URL) {
			throw new PangeaException(String.format("%s not supported. Use requestUploadURL() instead", tm), null);
		}

		if (tm == TransferMethod.POST_URL) {
			FileParams fileParams = Utils.getFileUploadParams(file);
			request.setCRC32C(fileParams.getCRC32C());
			request.setSHA256(fileParams.getSHA256());
			request.setSize(fileParams.getSize());
			name = "file";
		} else {
			name = "upload";
		}

		FileData fileData = new FileData(file, name);
		return post("/v1beta/sanitize", request, fileData, SanitizeResponse.class);
	}

	/**
	 * Sanitize via presigned URL
	 * @pangea.description Apply file sanitization actions according to specified rules via a presigned URL. Beta API.
	 * @pangea.operationId sanitize_post_v1beta_sanitize 2
	 * @param request Request parameters.
	 * @return A presigned URL.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var request = new SanitizeRequest.Builder()
	 *     .transferMethod(TransferMethod.PUT_URL)
	 *     .uploadedFileName("uploaded_file")
	 *     .build();
	 * var presignedUrl = client.requestUploadURL(request);
     *
	 * // Upload file to `acceptedResponse.getResult().getPutURL()`.
	 *
	 * // Poll for Sanitize's result.
	 * var response = client.pollResult(presignedUrl.getRequestId(), SanitizeResponse.class);
	 * }
	 */
	public AcceptedResponse requestUploadURL(SanitizeRequest request) throws PangeaException, PangeaAPIException {
		TransferMethod tm = request.getTransferMethod();
		if (tm == null) {
			tm = TransferMethod.PUT_URL;
		}

		if (tm == TransferMethod.MULTIPART || tm == TransferMethod.DEST_URL || tm == TransferMethod.SOURCE_URL) {
			throw new PangeaException(String.format("%s not supported. Use sanitize() instead", tm), null);
		}

		if (
			tm == TransferMethod.POST_URL &&
			(request.getCRC32c() == null || request.getSHA256() == null || request.getSize() == null)
		) {
			throw new PangeaException(
				String.format("Should set CRC32C, SHA256 and Size in order to use %s transfer method", tm),
				null
			);
		}
		return requestPresignedURL("/v1beta/sanitize", request);
	}
}
