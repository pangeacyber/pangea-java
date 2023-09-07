package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.FileIntelClient;
import cloud.pangeacyber.pangea.intel.requests.FileHashReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.FileReputationResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(FileIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        FileIntelClient client = new FileIntelClient.Builder(cfg).build();
        FileReputationResponse response = null;

        // Specify the path to the file you want to open
        String filePath = "./src/main/java/cloud/pangeacyber/examples/testfile.pdf";
        String hash = "";

        try {
            // Create an InputStream for the file
            InputStream inputStream = new FileInputStream(filePath);

            // Calculate SHA256 hash
            hash = DigestUtils.sha256Hex(inputStream);
        } catch (Exception e){
            System.out.println("Fail to calculate file hash: " + e);
            System.exit(1);
        }

        try {
            response = client.reputation(
			new FileHashReputationRequest .Builder(hash, "sha256")
				.provider("reversinglabs")
				.verbose(false)
				.raw(true)
				.build()
		);
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Reputation success");
        System.out.println("Reputation verdict: " + response.getResult().getData().getVerdict());
        System.out.println("Reputation raw data: " + response.getResult().getRawData());
    }
}
