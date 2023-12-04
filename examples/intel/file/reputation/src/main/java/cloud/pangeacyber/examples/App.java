package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.FileIntelClient;
import cloud.pangeacyber.pangea.intel.requests.FileHashReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.FileReputationResponse;
import cloud.pangeacyber.pangea.intel.models.FileReputationData;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

import java.util.Arrays;

import cloud.pangeacyber.pangea.Config;

public class App
{

    private static void printData(String indicator, FileReputationData data) {
        System.out.printf("\t Indicator: %s\n", indicator);
        System.out.printf("\t\t Verdict: %s\n", data.getVerdict());
        System.out.printf("\t\t Score: %d\n", data.getScore());
        System.out.printf("\t\t Category: %s\n", Arrays.toString(data.getCategory()));
    }

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
        String indicator = "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e";
        try {
            response = client.reputation(
			new FileHashReputationRequest .Builder(
				indicator,
				"sha256"
			)
				.provider("reversinglabs")
				.verbose(false)
				.raw(true)
				.build()
		);
        } catch (Exception e){
            System.out.println("Request failed: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printData(indicator, response.getResult().getData());
    }
}
