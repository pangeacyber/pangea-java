package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.file_scan.FileScanClient;
import cloud.pangeacyber.pangea.file_scan.models.FileScanData;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanRequest;
import cloud.pangeacyber.pangea.file_scan.responses.FileScanResponse;
import cloud.pangeacyber.pangea.exceptions.AcceptedResponse;
import cloud.pangeacyber.pangea.exceptions.AcceptedRequestException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PresignedURLException;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanUploadURLRequest;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.file_scan.FileUploader;
import cloud.pangeacyber.pangea.TransferMethod;
import java.util.Map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cloud.pangeacyber.pangea.Config;

public class App {
    final static String TESTFILE_PATH = "./src/main/java/cloud/pangeacyber/examples/testfile.pdf";

    public static void main(String[] args) throws Exception{
        System.out.println("File Scan start.");
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(FileScanClient.serviceName);
           	// To enable sync mode, set queuedRetryEnabled to true and set a timeout
            cfg.setQueuedRetryEnabled(true);
            cfg.setPollResultTimeout(120 * 1000);
        } catch (ConfigException e) {
            System.out.println(e);
            System.exit(1);
        }

        FileScanClient client = new FileScanClient.Builder(cfg).build();

        // This should be your own file to scan
		File file = new File(TESTFILE_PATH);

      	// Only the TransferMethod is needed when using TransferMethod.PUT_URL,
      	// in addition to the standard parameters

        // request an upload url
        FileScanUploadURLRequest request = new FileScanUploadURLRequest.Builder()
			.provider("reversinglabs")
			.raw(true)
			.verbose(false)
			.transferMethod(TransferMethod.PUT_URL)
			.build();

		AcceptedResponse acceptedResponse = client.requestUploadURL(request);

        //  extract upload url for posting the file
		String url = acceptedResponse.getResult().getPutURL();
        System.out.printf("Got presigned url: %s\n", url);

		FileData fileData = new FileData(file, "file");

        // Create an uploader and upload the file
		FileUploader fileUploader = new FileUploader.Builder().build();
		fileUploader.uploadFile(url, TransferMethod.PUT_URL, fileData);
        System.out.println("Upload file success");

        System.out.println("Let's try to poll result...");
		FileScanResponse response;
		int maxRetry = 12;
		for (int retry = 0; retry < maxRetry; retry++) {
			try {
				Thread.sleep(10 * 1000);

                // Poll for results, multiple polling attempts may be required
				response = client.pollResult(acceptedResponse.getRequestId(), FileScanResponse.class);

				FileScanData data = response.getResult().getData();
                System.out.println("Reputation success");
                System.out.println("Reputation verdict: " + data.getVerdict());
                System.out.println("Reputation raw data: " + response.getResult().getRawData());
				break;
			} catch (PangeaAPIException e) {
                System.out.println("Result in not ready yet");
			}
		}
    }
}
