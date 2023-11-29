package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.requests.IPReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.IPReputationResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
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
        try {
            response = client.reputation(
                new IPReputationRequest.Builder("93.231.182.110").provider("crowdstrike").verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Request failed: " + e);
            System.exit(1);
        }

        System.out.println("Reputation success");
        System.out.println("Reputation verdict: " + response.getResult().getData().getVerdict());
        System.out.println("Reputation raw data: " + response.getResult().getRawData());
    }
}
