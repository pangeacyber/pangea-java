package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.DomainIntelClient;
import cloud.pangeacyber.pangea.intel.models.DomainReputationData;
import cloud.pangeacyber.pangea.intel.requests.DomainReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.DomainReputationResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

import java.util.Arrays;

import cloud.pangeacyber.pangea.Config;

public class App
{
    private static void printData(String indicator, DomainReputationData data) {
        System.out.printf("\t Indicator: %s\n", indicator);
        System.out.printf("\t\t Verdict: %s\n", data.getVerdict());
        System.out.printf("\t\t Score: %d\n", data.getScore());
        System.out.printf("\t\t Category: %s\n", Arrays.toString(data.getCategory()));
    }

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
        String indicator = "737updatesboeing.com";
        try {
            response = client.reputation(
                new DomainReputationRequest.Builder(indicator)
                    .provider("domaintools")
                    .verbose(true)
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
