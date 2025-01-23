package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.file_scan.FileScanClient;
import cloud.pangeacyber.pangea.file_scan.models.FileScanData;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanRequest;
import cloud.pangeacyber.pangea.file_scan.responses.FileScanResponse;
import cloud.pangeacyber.pangea.exceptions.AcceptedRequestException;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cloud.pangeacyber.pangea.Config;

public class App {
    final static String TESTFILE_PATH = "./src/main/java/cloud/pangeacyber/examples/testfile.pdf";

    public static void main(String[] args) {
        System.out.println("File Scan sync start.");
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(FileScanClient.serviceName);
           	// To enable sync mode, set queuedRetryEnabled to true and set a timeout
            cfg.setQueuedRetryEnabled(true);
            cfg.setPollResultTimeout(120);
        } catch (ConfigException e) {
            System.out.println(e);
            System.exit(1);
        }

        FileScanClient client = new FileScanClient.Builder(cfg).build();
        FileScanResponse response = null;
        try {
            // This should be your own file to scan
            File file = new File(TESTFILE_PATH);

            System.out.println("File Scan request...");
            response = client.scan(
                    new FileScanRequest.Builder().provider("crowdstrike")
                            .raw(true)
                            .build(),
                    file);
		} catch (AcceptedRequestException e) {
			System.out.println("Request will be completed at a later time.");
			System.exit(0);
        } catch (Exception e) {
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }

        System.out.println("File Scan success.");
        FileScanData data = response.getResult().getData();
        System.out.println("Reputation success");
        System.out.println("Reputation verdict: " + data.getVerdict());
        System.out.println("Reputation raw data: " + response.getResult().getRawData());
    }
}
