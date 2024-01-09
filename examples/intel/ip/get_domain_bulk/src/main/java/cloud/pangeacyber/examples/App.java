package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPDomainBulkData;
import cloud.pangeacyber.pangea.intel.models.IPDomainData;
import cloud.pangeacyber.pangea.intel.requests.IPDomainBulkRequest;
import cloud.pangeacyber.pangea.intel.responses.IPDomainBulkResponse;

import java.util.Map;

import cloud.pangeacyber.pangea.Config;

public class App
{

    public static void printData(String ip, IPDomainData data) {
        if(data.isDomainFound()) {
            System.out.printf("\t IP %s domain is %s\n", ip, data.getDomain());
        } else {
            System.out.printf("\t IP %s domain not found\n", ip);
        }
    }

    private static void printBulkData(IPDomainBulkData data) {
        for (Map.Entry<String, IPDomainData> entry : data.entrySet()) {
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
        IPDomainBulkResponse response = null;
        try {
            String[] ips = { "93.231.182.110", "190.28.74.251" };
            response = client.getDomainBulk(
                new IPDomainBulkRequest.Builder(ips).verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printBulkData(response.getResult().getData());
    }
}
