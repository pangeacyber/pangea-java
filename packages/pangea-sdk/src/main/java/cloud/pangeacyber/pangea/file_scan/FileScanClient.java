package cloud.pangeacyber.pangea.file_scan;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.AcceptedResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanRequest;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanUploadURLRequest;
import cloud.pangeacyber.pangea.file_scan.responses.FileScanResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.File;

final class FileScanFullRequest extends FileScanRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("crc32c")
	String crc32c;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha256")
	String sha256;

	public FileScanFullRequest(FileScanRequest request, FileParams params) {
		super(request);
		this.size = params.getSize();
		this.crc32c = params.getCRC32C();
		this.sha256 = params.getSHA256();
	}

	public FileScanFullRequest(FileScanUploadURLRequest request) {
		this.raw = request.getRaw();
		this.verbose = request.getVerbose();
		this.provider = request.getProvider();
		this.setTransferMethod(request.getTransferMethod());
		FileParams params = request.getFileParams();
		if (params != null) {
			this.size = params.getSize();
			this.crc32c = params.getCRC32C();
			this.sha256 = params.getSHA256();
		}
	}

	public FileScanFullRequest(FileScanRequest request) {
		super(request);
		this.size = null;
		this.crc32c = null;
		this.sha256 = null;
	}

	public Integer getSize() {
		return size;
	}

	public String getCRC32c() {
		return crc32c;
	}

	public String getSHA256() {
		return sha256;
	}
}

public class FileScanClient extends BaseClient {

	public static String serviceName = "file-scan";

	public FileScanClient(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public FileScanClient build() {
			return new FileScanClient(this);
		}
	}

	/**
	 * Scan
	 * @pangea.description Scan a file for malicious content.
	 * @pangea.operationId file_scan_post_v1_scan
	 * @param request
	 * @param file
	 * @return FileScanResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * File file = new File("./path/to/file.pdf");
	 *
	 * FileScanResponse response = client.scan(
	 * 	new FileScanRequest.Builder().provider("crowdstrike")
	 * 		.raw(true)
	 * 		.build(),
	 * 	file);
	 * }
	 */
	public FileScanResponse scan(FileScanRequest request, File file) throws PangeaException, PangeaAPIException {
		FileScanFullRequest fullRequest;
		TransferMethod tm = request.getTransferMethod();
		String name;

		if (tm == TransferMethod.PUT_URL) {
			throw new PangeaException(String.format("%s not supported. Use GetUploadURL() instead", tm), null);
		}

		if (tm == TransferMethod.POST_URL) {
			FileParams fileParams = Utils.getFileUploadParams(file);
			fullRequest = new FileScanFullRequest(request, fileParams);
			name = "file";
		} else {
			fullRequest = new FileScanFullRequest(request);
			name = "upload";
		}

		FileData fileData = new FileData(file, name);
		return post("/v1/scan", fullRequest, fileData, FileScanResponse.class);
	}

	public AcceptedResponse requestUploadURL(FileScanUploadURLRequest request)
		throws PangeaException, PangeaAPIException {
		TransferMethod tm = request.getTransferMethod();
		if (tm == null) {
			tm = TransferMethod.PUT_URL;
		}

		if (tm == TransferMethod.MULTIPART) {
			throw new PangeaException(String.format("%s not supported. Use scan() instead", tm), null);
		}

		if (tm == TransferMethod.POST_URL && request.getFileParams() == null) {
			throw new PangeaException(
				String.format("Should set FileParams in order to use %s transfer method", tm),
				null
			);
		}

		FileScanFullRequest fullRequest = new FileScanFullRequest(request);
		return requestPresignedURL("/v1/scan", fullRequest);
	}
}
