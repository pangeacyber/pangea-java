package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.requests.IPProxyRequest;
import cloud.pangeacyber.pangea.intel.responses.IPProxyResponse;
import cloud.pangeacyber.pangea.Config;

public class App
{
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
        try {
            response = client.isProxy(
                new IPProxyRequest.Builder("34.201.32.172").provider("digitalelement").verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Request failed: " + e);
            System.exit(1);
        }

        System.out.println("Proxy check success");
        System.out.println("Is proxy?: " + response.getResult().getData().isProxy());
        System.out.println("Proxy raw data: " + response.getResult().getRawData());
    }
}
