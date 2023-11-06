package cloud.pangeacyber.pangea.file_scan;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanRequest;
import cloud.pangeacyber.pangea.file_scan.responses.FileScanResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.File;

final class FileScanFullRequest extends FileScanRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transfer_size")
	Integer size;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transfer_crc32c")
	String crc32c;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transfer_sha256")
	String sha256;

	public FileScanFullRequest(FileScanRequest request, FileParams params) {
		super(request);
		this.size = params.getSize();
		this.crc32c = params.getCRC32C();
		this.sha256 = params.getSHA256();
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
		if (request.getTransferMethod() == TransferMethod.DIRECT) {
			FileParams fileParams = Utils.getFSparams(file);
			fullRequest = new FileScanFullRequest(request, fileParams);
		} else {
			fullRequest = new FileScanFullRequest(request);
		}

		return post("/v1/scan", fullRequest, file, FileScanResponse.class);
	}
}
