package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPProxyData;
import cloud.pangeacyber.pangea.intel.models.IPProxyBulkData;
import cloud.pangeacyber.pangea.intel.requests.IPProxyBulkRequest;
import cloud.pangeacyber.pangea.intel.responses.IPProxyBulkResponse;
import cloud.pangeacyber.pangea.Config;

import java.util.Map;

public class App
{

    private static void printData(String ip, IPProxyData data) {
        if(data.isProxy()){
            System.out.printf("\t IP %s is a proxy\n", ip);
        } else {
            System.out.printf("\t IP %s is not a proxy\n", ip);
        }
    }

    private static void printBulkData(IPProxyBulkData data) {
        for (Map.Entry<String, IPProxyData> entry : data.entrySet()) {
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
        IPProxyBulkResponse response = null;
        try {
            String[] ips = { "34.201.32.172", "190.28.74.251" };
            response = client.isProxyBulk(
                new IPProxyBulkRequest.Builder(ips).verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Failed to perform request: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printBulkData(response.getResult().getData());
    }
}
