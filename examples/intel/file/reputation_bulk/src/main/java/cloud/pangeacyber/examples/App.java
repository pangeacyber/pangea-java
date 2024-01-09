package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.FileIntelClient;
import cloud.pangeacyber.pangea.intel.requests.FileHashReputationBulkRequest;
import cloud.pangeacyber.pangea.intel.responses.FileReputationBulkResponse;
import cloud.pangeacyber.pangea.intel.models.FileReputationData;
import cloud.pangeacyber.pangea.intel.models.FileReputationBulkData;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

import java.util.Arrays;
import java.util.Map;

import cloud.pangeacyber.pangea.Config;

public class App
{

    private static void printData(String indicator, FileReputationData data) {
        System.out.printf("\t Indicator: %s\n", indicator);
        System.out.printf("\t\t Verdict: %s\n", data.getVerdict());
        System.out.printf("\t\t Score: %d\n", data.getScore());
        System.out.printf("\t\t Category: %s\n", Arrays.toString(data.getCategory()));
    }

    private static void printBulkData(FileReputationBulkData data) {
        for (Map.Entry<String, FileReputationData> entry : data.entrySet()) {
            printData(entry.getKey(), entry.getValue());
        }
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
        FileReputationBulkResponse response = null;
        try {
            String[] hashes = {
                "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
                "179e2b8a4162372cd9344b81793cbf74a9513a002eda3324e6331243f3137a63",
            };

            response = client.reputationBulk(
                new FileHashReputationBulkRequest.Builder(hashes, "sha256")
                    .provider("reversinglabs")
                    .verbose(true)
                    .raw(true)
                    .build()
            );
        } catch (Exception e){
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printBulkData(response.getResult().getData());
    }
}
