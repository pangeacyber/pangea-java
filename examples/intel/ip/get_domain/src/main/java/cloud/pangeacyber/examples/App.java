package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.IpIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPDomainResponse;
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

        IpIntelClient client = new IpIntelClient.Builder(cfg).build();
        IPDomainResponse response = null;
        try {
            response = client.getDomain("24.235.114.61", "digitalelement", true, true);
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Get domain success");
        System.out.println("Domain name: " + response.getResult().getData().getDomain());
        System.out.println("Domain raw data: " + response.getResult().getRawData());
    }
}
