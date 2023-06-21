package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.DomainIntelClient;
import cloud.pangeacyber.pangea.intel.requests.DomainReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.DomainReputationResponse;
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
        DomainReputationResponse response = null;
        try {
            response = client.reputation(
                new DomainReputationRequest.Builder("737updatesboeing.com")
                    .provider("domaintools")
                    .verbose(true)
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
