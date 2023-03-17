package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.IpIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPProxyResponse;
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
        IPProxyResponse response = null;
        try {
            response = client.isProxy("1.0.136.28", "digitalelement", true, true);
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Proxy check success");
        System.out.println("Is proxy?: " + response.getResult().getData().isProxy());
        System.out.println("Proxy raw data: " + response.getResult().getRawData());
    }
}
