package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.requests.IPGeolocateRequest;
import cloud.pangeacyber.pangea.intel.responses.IPGeolocateResponse;
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
        IPGeolocateResponse response = null;
        try {
            response = client.geolocate(
    			new IPGeolocateRequest.Builder("93.231.182.110").provider("digitalelement").verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Fail to perfom request: " + e);
            System.exit(1);
        }

        System.out.println("Geolocate success");
        System.out.println("Geolocate country: " + response.getResult().getData().getCountry());
        System.out.println("Geolocate raw data: " + response.getResult().getRawData());
    }
}
