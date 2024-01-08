package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPVPNData;
import cloud.pangeacyber.pangea.intel.models.IPVPNBulkData;
import cloud.pangeacyber.pangea.intel.requests.IPVPNBulkRequest;
import cloud.pangeacyber.pangea.intel.responses.IPVPNBulkResponse;

import cloud.pangeacyber.pangea.Config;
import java.util.Map;

public class App
{

    private static void printData(String ip, IPVPNData data) {
        if(data.isVPN()){
            System.out.printf("\t IP %s is a VPN\n", ip);
        } else {
            System.out.printf("\t IP %s is not a VPN\n", ip);
        }
    }

    private static void printBulkData(IPVPNBulkData data) {
        for (Map.Entry<String, IPVPNData> entry : data.entrySet()) {
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
        IPVPNBulkResponse response = null;
        try {
            String[] ips = { "2.56.189.74", "190.28.74.251" };
            response = client.isVPNBulk(
                new IPVPNBulkRequest.Builder(ips).verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printBulkData(response.getResult().getData());
    }
}
