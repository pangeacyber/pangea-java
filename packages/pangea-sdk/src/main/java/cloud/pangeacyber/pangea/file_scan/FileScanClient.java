package cloud.pangeacyber.pangea.file_scan;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanRequest;
import cloud.pangeacyber.pangea.file_scan.responses.FileScanResponse;
import java.io.File;

public class FileScanClient extends BaseClient {

	public static String serviceName = "file-scan";
	private static final boolean supportMultiConfig = false;

	public FileScanClient(Builder builder) {
		super(builder, serviceName, supportMultiConfig);
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
		return post("/v1/scan", request, file, FileScanResponse.class);
	}
}
