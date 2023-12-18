package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.intel.IPIntelClient;
import cloud.pangeacyber.pangea.intel.models.IPVPNData;
import cloud.pangeacyber.pangea.intel.requests.IPVPNRequest;
import cloud.pangeacyber.pangea.intel.responses.IPVPNResponse;

import cloud.pangeacyber.pangea.Config;

public class App
{

    private static void printData(String ip, IPVPNData data) {
        if(data.isVPN()){
            System.out.printf("\t IP %s is a VPN\n", ip);
        } else {
            System.out.printf("\t IP %s is not a VPN\n", ip);
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
        IPVPNResponse response = null;
        String ip = "2.56.189.74";
        try {
            response = client.isVPN(
                new IPVPNRequest.Builder(ip).provider("digitalelement").verbose(true).raw(true).build()
            );
        } catch (Exception e){
            System.out.println("Request failed: " + e);
            System.exit(1);
        }

        System.out.println("Result:");
        printData(ip, response.getResult().getData());
    }
}
