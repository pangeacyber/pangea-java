package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.URLIntelClient;
import cloud.pangeacyber.pangea.intel.requests.URLReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.URLReputationResponse;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(URLIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        URLIntelClient client = new URLIntelClient.Builder(cfg).build();
        URLReputationResponse response = null;
        try {
            response = client.reputation(
                new URLReputationRequest.Builder("http://113.235.101.11:54384")
                    .provider("crowdstrike")
                    .verbose(true)
                    .raw(true)
                    .build()
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
