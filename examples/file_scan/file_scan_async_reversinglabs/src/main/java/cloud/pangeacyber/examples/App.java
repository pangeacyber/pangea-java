package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.file_scan.FileScanClient;
import cloud.pangeacyber.pangea.file_scan.models.FileScanData;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanRequest;
import cloud.pangeacyber.pangea.file_scan.responses.FileScanResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cloud.pangeacyber.pangea.Config;

public class App {
    final static String TESTFILE_PATH = "./src/main/java/cloud/pangeacyber/examples/testfile.pdf";

    public static void main(String[] args) throws Exception, PangeaException, PangeaAPIException {
        System.out.println("File Scan sync start.");
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(FileScanClient.serviceName);
            // To enable async mode, set queuedRetryEnabled to false
            // When .Scan() is called it will return an AcceptedRequestException immediately
            // when the server returns a 202 response
            cfg.setQueuedRetryEnabled(false);
        } catch (ConfigException e) {
            System.out.println(e);
            System.exit(1);
        }

        FileScanClient client = new FileScanClient.Builder(cfg).build();
        FileScanResponse response = null;
        AcceptedRequestException exception = null;
        try {
            System.out.println("File Scan request...");
            // This should be your own file to scan
            File file = new File(TESTFILE_PATH);
            response = client.scan(
                    new FileScanRequest.Builder().provider("reversinglabs")
                            .raw(true)
                            .build(),
                    file);

            System.out.println("File Scan success on first attempt.");
            FileScanData data = response.getResult().getData();
            System.out.println("Reputation success");
            System.out.println("Reputation verdict: " + data.getVerdict());
            System.out.println("Reputation raw data: " + response.getResult().getRawData());
            System.exit(0);
        } catch (AcceptedRequestException e) {
            System.out.println("AcceptedRequestException as expected");
            exception = e;
        }  catch (Exception e) {
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }

        System.out.println("File Scan sleep until result should be ready.");
        // Sleep 20 seconds until result is (should) be ready
        Thread.sleep(20 * 1000);

        System.out.println("File Scan poll result...");
        try {
            // Poll for results, multiple polling attempts may be required
            response = client.pollResult(exception.getRequestId(), FileScanResponse.class);

            System.out.println("File Scan poll result success.");
            FileScanData data = response.getResult().getData();
            System.out.println("Reputation success");
            System.out.println("Reputation verdict: " + data.getVerdict());
            System.out.println("Reputation raw data: " + response.getResult().getRawData());
        } catch (AcceptedRequestException e) {
            System.out.println("Poll result failed: " + e.toString());
        } catch (Exception e) {
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }
    }
}
