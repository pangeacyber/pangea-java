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

	public FileScanResponse scan(FileScanRequest request, File file) throws PangeaException, PangeaAPIException {
		return post("/v1/scan", request, file, FileScanResponse.class);
	}
}