package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.FileScanRequest;
import cloud.pangeacyber.pangea.intel.responses.FileScanResponse;

import java.io.File;

public class FileScanClient extends Client {

	public static String serviceName = "file-scan";

	public FileScanClient(Config config) {
		super(config, serviceName);
	}

	public FileScanResponse scan(FileScanRequest request, File file) throws PangeaException, PangeaAPIException {
		return doPost("/v1/scan", request, file, FileScanResponse.class);
	}
}
