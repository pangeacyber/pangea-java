package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.IpIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPReputationResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(IpIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        IpIntelClient client = new IpIntelClient(cfg);
        IPReputationResponse response = null;
        try {
            response = client.reputation("93.231.182.110", "crowdstrike", true, true);
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Reputation success");
        System.out.println("Reputation verdict: " + response.getResult().getData().getVerdict());
        System.out.println("Reputation raw data: " + response.getResult().getRawData());
    }
}
