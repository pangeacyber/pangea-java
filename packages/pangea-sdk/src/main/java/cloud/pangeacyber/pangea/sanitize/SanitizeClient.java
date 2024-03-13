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

public class SanitizeClient extends BaseClient {

	public static String serviceName = "sanitize";

	public SanitizeClient(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public SanitizeClient build() {
			return new SanitizeClient(this);
		}
	}

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
