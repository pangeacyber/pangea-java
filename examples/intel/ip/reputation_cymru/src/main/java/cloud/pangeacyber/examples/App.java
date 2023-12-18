package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPReputationData;
import cloud.pangeacyber.pangea.intel.requests.IPReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.IPReputationResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

import java.util.Arrays;

public class App
{

    private static void printData(String indicator, IPReputationData data) {
        System.out.printf("\t Indicator: %s\n", indicator);
        System.out.printf("\t\t Verdict: %s\n", data.getVerdict());
        System.out.printf("\t\t Score: %d\n", data.getScore());
        System.out.printf("\t\t Category: %s\n", Arrays.toString(data.getCategory()));
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
        IPReputationResponse response = null;
        String ip = "93.231.182.110";
        try {
            response = client.reputation(
                new IPReputationRequest.Builder(ip).provider("cymru").verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Request failed: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printData(ip, response.getResult().getData());
    }
}
