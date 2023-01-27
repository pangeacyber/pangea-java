package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.UrlIntelClient;
import cloud.pangeacyber.pangea.intel.URLReputationResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(DomainIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        UrlIntelClient client = new UrlIntelClient(cfg);
        URLReputationResponse response = null;
        try {
            response = client.reputation("http://113.235.101.11:54384", "crowdstrike", true, true);
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Reputation success");
        System.out.println("Reputation data: " + response.getResult().getData());
        System.out.println("Reputation raw data: " + response.getResult().getRawData());
    }
}
