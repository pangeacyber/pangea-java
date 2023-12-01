package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.requests.IPVPNRequest;
import cloud.pangeacyber.pangea.intel.responses.IPVPNResponse;
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
        IPVPNResponse response = null;
        try {
            response = client.isVPN(
                new IPVPNRequest.Builder("2.56.189.74").provider("digitalelement").verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Request failed: " + e);
            System.exit(1);
        }

        System.out.println("VPN check success");
        System.out.println("Is VPN?: " + response.getResult().getData().isVPN());
        System.out.println("VPN raw data: " + response.getResult().getRawData());
    }
}
