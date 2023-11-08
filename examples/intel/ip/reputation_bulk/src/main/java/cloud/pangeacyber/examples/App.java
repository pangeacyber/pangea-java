package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPReputationData;
import cloud.pangeacyber.pangea.intel.models.IPReputationBulkData;
import cloud.pangeacyber.pangea.intel.requests.IPReputationBulkRequest;
import cloud.pangeacyber.pangea.intel.responses.IPReputationBulkResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

import java.util.Arrays;
import java.util.Map;

import cloud.pangeacyber.pangea.Config;

public class App
{

    private static void printData(String indicator, IPReputationData data) {
        System.out.printf("\t Indicator: %s\n", indicator);
        System.out.printf("\t\t Verdict: %s\n", data.getVerdict());
        System.out.printf("\t\t Score: %d\n", data.getScore());
        System.out.printf("\t\t Category: %s\n", Arrays.toString(data.getCategory()));
    }

    private static void printBulkData(IPReputationBulkData data) {
        for (Map.Entry<String, IPReputationData> entry : data.entrySet()) {
            printData(entry.getKey(), entry.getValue());
        }
    }

    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(IPIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        IPIntelClient client = new IPIntelClient.Builder(cfg).build();
        IPReputationBulkResponse response = null;
        try {
            String[] ips = { "93.231.182.110", "190.28.74.251" };
            response = client.reputationBulk(
                new IPReputationBulkRequest.Builder(ips).provider("crowdstrike").verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printBulkData(response.getResult().getData());
    }
}
