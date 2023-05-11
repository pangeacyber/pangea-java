package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.FileScanRequest;
import cloud.pangeacyber.pangea.intel.responses.FileScanResponse;

import java.io.File;

public class FileScanClient extends BaseClient {
	public FileScanClient(Builder builder) {
		super(builder);
	}

	public static class Builder extends BaseClient.Builder<Builder> {
		public Builder(Config config) {
			super(config, "file-scan");
		}

		public FileScanClient build() {
			return new FileScanClient(this);
		}
	}

	public FileScanResponse scan(FileScanRequest request, File file) throws PangeaException, PangeaAPIException {
		return post("/v1/scan", request, file, FileScanResponse.class);
	}
}
