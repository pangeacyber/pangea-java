package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPProxyData;
import cloud.pangeacyber.pangea.intel.requests.IPProxyRequest;
import cloud.pangeacyber.pangea.intel.responses.IPProxyResponse;
import cloud.pangeacyber.pangea.Config;

public class App
{

    private static void printData(String ip, IPProxyData data) {
        if(data.isProxy()){
            System.out.printf("\t IP %s is a proxy\n", ip);
        } else {
            System.out.printf("\t IP %s is not a proxy\n", ip);
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
        IPProxyResponse response = null;
        String ip = "34.201.32.172";
        try {
            response = client.isProxy(
                new IPProxyRequest.Builder(ip).provider("digitalelement").verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Request failed: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printData(ip, response.getResult().getData());
    }
}
