package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.IpIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPVPNResponse;
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
        IPVPNResponse response = null;
        try {
            response = client.isVPN("2.56.189.74", "digitalelement", true, true);
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("VPN check success");
        System.out.println("Is VPN?: " + response.getResult().getData().isVPN());
        System.out.println("VPN raw data: " + response.getResult().getRawData());
    }
}
