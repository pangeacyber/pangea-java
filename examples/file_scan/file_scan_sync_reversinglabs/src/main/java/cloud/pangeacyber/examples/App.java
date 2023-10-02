package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.file_scan.FileScanClient;
import cloud.pangeacyber.pangea.file_scan.models.FileScanData;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanRequest;
import cloud.pangeacyber.pangea.file_scan.responses.FileScanResponse;
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
            // To work in sync it's need to set up queuedRetryEnabled to true and set up a
            // proper timeout
            // If timeout it's so little service won't end up and will return an
            // AcceptedRequestException anyway
            cfg.setQueuedRetryEnabled(true);
            cfg.setPollResultTimeout(120 * 1000);
        } catch (ConfigException e) {
            System.out.println(e);
            System.exit(1);
        }

        FileScanClient client = new FileScanClient.Builder(cfg).build();
        FileScanResponse response = null;
        try {
            // Here we create a file that will give us a malicious result as example
            // This should be your own file to scan
            File file = new File(TESTFILE_PATH);

            System.out.println("File Scan request...");
            response = client.scan(
                    new FileScanRequest.Builder().provider("reversinglabs")
                            .raw(true)
                            .build(),
                    file);

        } catch (Exception e) {
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("File Scan success.");
        FileScanData data = response.getResult().getData();
        System.out.println("Reputation success");
        System.out.println("Reputation verdict: " + data.getVerdict());
        System.out.println("Reputation raw data: " + response.getResult().getRawData());
    }
}
