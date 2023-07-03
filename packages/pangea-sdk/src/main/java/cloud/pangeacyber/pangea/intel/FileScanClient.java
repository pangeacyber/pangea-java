package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.models.FileScanRequest;
import cloud.pangeacyber.pangea.intel.models.FileScanResponse;
import java.io.File;

public class FileScanClient extends Client {

	public static String serviceName = "file-scan";
	private static final boolean supportMultiConfig = false;

	public FileScanClient(Config config) {
		super(config, serviceName, supportMultiConfig);
	}

	public FileScanResponse scan(FileScanRequest request, File file) throws PangeaException, PangeaAPIException {
		return doPost("/v1/scan", request, file, FileScanResponse.class);
	}
}
