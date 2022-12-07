package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.embargo.EmbargoClient;
import cloud.pangeacyber.pangea.embargo.IpCheckResponse;
import cloud.pangeacyber.pangea.embargo.EmbargoSanction;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(EmbargoClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        EmbargoClient client = new EmbargoClient(cfg);
        String ip = "213.24.238.26";
        IpCheckResponse response = null;
        try {
            response = client.ipCheck(ip);
        } catch (Exception e){
            System.out.println("Fail ip check: " + e);
            System.exit(1);
        }

        System.out.println("Check success");
        System.out.println("IP " + ip + " has " + response.getResult().getCount() + " sanction(s)");
        for(EmbargoSanction sanction: response.getResult().getSanctions()){
            System.out.println("Embargoed country: " + sanction.getEmbargoedCountryName()
            + ". Issuing country: " + sanction.getIssuingCountry()
            + ". List name: " + sanction.getListName());
        }
    }
}
