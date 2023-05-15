package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.FileScanClient;
import cloud.pangeacyber.pangea.intel.models.FileScanData;
import cloud.pangeacyber.pangea.intel.models.FileScanRequest;
import cloud.pangeacyber.pangea.intel.models.FileScanResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

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

    public static void main( String[] args )
    {
        System.out.println("File Scan sync start.");
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(FileScanClient.serviceName);
            // To work in sync it's need to set up queuedRetryEnabled to true and set up a proper timeout
            // If timeout it's so little service won't end up and will return an AcceptedRequestException anyway
            cfg.setQueuedRetryEnabled(true);
            cfg.setPollResultTimeout(120*1000);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        FileScanClient client = new FileScanClient(cfg);
        FileScanResponse response = null;
        try {
            // Here we create a file that will give us a malicious result as example
            // This should be your own file to scan
            File file = eicar();

            System.out.println("File Scan request...");
            response = client.scan(
                new FileScanRequest.Builder().setProvider("reversinglabs")
                    .setRaw(true)
                    .build(),
                file
            );

        } catch (Exception e){
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
