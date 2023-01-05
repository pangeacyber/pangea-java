package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.embargo.EmbargoClient;
import cloud.pangeacyber.pangea.embargo.IsoCheckResponse;
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
        String code = "CU";
        IsoCheckResponse response = null;
        try {
            response = client.isoCheck(code);
        } catch (Exception e){
            System.out.println("Fail iso check: " + e);
            System.exit(1);
        }

        System.out.println(String.format("Checking Embargo ISO code: '%s'", code));
        System.out.println("ISO code " + code + " has " + response.getResult().getCount() + " sanction(s)");
        for(EmbargoSanction sanction: response.getResult().getSanctions()){
            System.out.println("Embargoed country: " + sanction.getEmbargoedCountryName()
            + ". Issuing country: " + sanction.getIssuingCountry()
            + ". List name: " + sanction.getListName());
        }
    }
}
