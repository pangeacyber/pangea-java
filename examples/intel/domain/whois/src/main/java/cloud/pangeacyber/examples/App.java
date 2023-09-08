package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.DomainIntelClient;
import cloud.pangeacyber.pangea.intel.requests.DomainWhoIsRequest;
import cloud.pangeacyber.pangea.intel.responses.DomainWhoIsResponse;
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

        DomainIntelClient client = new DomainIntelClient.Builder(cfg).build();
        DomainWhoIsResponse response = null;
        try {
            response = client.whoIs(
                new DomainWhoIsRequest.Builder("737updatesboeing.com")
                    .provider("whoisxml")
                    .verbose(true)
                    .raw(true)
                    .build()
            );
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("WhoIs success");
        System.out.println("DomainName: " + response.getResult().getData().getDomainName());
        System.out.println("DomainAvailability: " + response.getResult().getData().getDomainAvailability());
    }
}
