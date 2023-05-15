package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.FileScanClient;
import cloud.pangeacyber.pangea.intel.models.FileScanData;
import cloud.pangeacyber.pangea.intel.models.FileScanRequest;
import cloud.pangeacyber.pangea.intel.models.FileScanResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cloud.pangeacyber.pangea.Config;

public class App
{
    final static String EICAR = "X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*\n";

	public static File eicar() throws IOException {
		File file = new File("file.exe");
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(EICAR.getBytes());
		}

		return file;
	}

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

        FileScanClient client = new FileScanClient(cfg);
        FileScanResponse response = null;
		AcceptedRequestException exception = null;
		try {
            System.out.println("File Scan request...");
            // Here we create a file that will give us a malicious result as example
            // This should be your own file to scan
            File file = eicar();
            response = client.scan(
                new FileScanRequest.Builder().setProvider("reversinglabs")
                    .setRaw(true)
                    .build(),
                file
            );
		} catch (AcceptedRequestException e) {
            System.out.println("AcceptedRequestException as expected");
			exception = e;
		}

        System.out.println("File Scan sleep until result should be ready.");
		// Sleep 30 seconds until result is (should) be ready
		Thread.sleep(30 * 1000);

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
