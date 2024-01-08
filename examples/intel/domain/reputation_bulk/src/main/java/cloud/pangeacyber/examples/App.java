package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.DomainIntelClient;
import cloud.pangeacyber.pangea.intel.models.DomainReputationData;
import cloud.pangeacyber.pangea.intel.models.DomainReputationBulkData;
import cloud.pangeacyber.pangea.intel.requests.DomainReputationBulkRequest;
import cloud.pangeacyber.pangea.intel.responses.DomainReputationBulkResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

import java.util.Arrays;
import java.util.Map;

import cloud.pangeacyber.pangea.Config;

public class App
{
    private static void printData(String indicator, DomainReputationData data) {
        System.out.printf("\t Indicator: %s\n", indicator);
        System.out.printf("\t\t Verdict: %s\n", data.getVerdict());
        System.out.printf("\t\t Score: %d\n", data.getScore());
        System.out.printf("\t\t Category: %s\n", Arrays.toString(data.getCategory()));
    }

    private static void printBulkData(DomainReputationBulkData data) {
        for (Map.Entry<String, DomainReputationData> entry : data.entrySet()) {
            printData(entry.getKey(), entry.getValue());
        }
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
        DomainReputationBulkResponse response = null;
        try {
            String[] domainList = { "pemewizubidob.cafij.co.za", "redbomb.com.tr", "kmbk8.hicp.net" };
            response = client.reputationBulk(
                new DomainReputationBulkRequest.Builder(domainList).provider("crowdstrike").verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printBulkData(response.getResult().getData());
    }
}
