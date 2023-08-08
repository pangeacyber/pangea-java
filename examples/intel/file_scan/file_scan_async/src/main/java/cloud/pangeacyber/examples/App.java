package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.FileScanClient;
import cloud.pangeacyber.pangea.intel.models.FileScanData;
import cloud.pangeacyber.pangea.intel.requests.FileScanRequest;
import cloud.pangeacyber.pangea.intel.responses.FileScanResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cloud.pangeacyber.pangea.Config;

public class App
{
    final static String TESTFILE_PATH = "./src/main/java/cloud/pangeacyber/examples/testfile.pdf";

    public static void main( String[] args ) throws Exception, PangeaException, PangeaAPIException
    {
        System.out.println("File Scan sync start.");
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(FileScanClient.serviceName);
            // To work in async it's need to set up queuedRetryEnabled to false
            // When we call .scan() it will return an AcceptedRequestException inmediatly if server return a 202 response
            cfg.setQueuedRetryEnabled(false);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        FileScanClient client = new FileScanClient.Builder(cfg).build();
        FileScanResponse response = null;
	    AcceptedRequestException exception = null;
		try {
            System.out.println("File Scan request...");
            // Here we create a file that will give us a malicious result as example
            // This should be your own file to scan
            File file = new File(TESTFILE_PATH);
            response = client.scan(
                new FileScanRequest.Builder().provider("crowdstrike")
                    .raw(true)
                    .build(),
                file
            );
		} catch (AcceptedRequestException e) {
            System.out.println("AcceptedRequestException as expected");
			exception = e;
		}

        System.out.println("File Scan sleep until result should be ready.");
		// Sleep 20 seconds until result is (should) be ready
		Thread.sleep(20 * 1000);

        System.out.println("File Scan poll result...");
		// Poll result, this could raise another AcceptedRequestException if result is not ready
		response = client.pollResult(exception.getRequestId(), FileScanResponse.class);

        System.out.println("File Scan poll result success.");
        FileScanData data = response.getResult().getData();
        System.out.println("Reputation success");
        System.out.println("Reputation verdict: " + data.getVerdict());
        System.out.println("Reputation raw data: " + response.getResult().getRawData());
    }
}
