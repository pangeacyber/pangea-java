package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.IpIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPGeolocateResponse;
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
        IPGeolocateResponse response = null;
        try {
            response = client.geolocate("93.231.182.110", "digitalenvoy", true, true);
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Geolocate success");
        System.out.println("Geolocate country: " + response.getResult().getData().getCountry());
        System.out.println("Geolocate raw data: " + response.getResult().getRawData());
    }
}
