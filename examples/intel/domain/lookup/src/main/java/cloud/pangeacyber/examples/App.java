package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.intel.DomainIntelClient;
import cloud.pangeacyber.pangea.intel.DomainLookupResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(DomainIntelClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        DomainIntelClient client = new DomainIntelClient(cfg);
        DomainLookupResponse response = null;
        try {
            response = client.lookup("737updatesboeing.com", "domaintools", true, true);
        } catch (Exception e){
            System.out.println("Fail to perfom lookup: " + e);
            System.exit(1);
        }

        System.out.println("Lookup success");
        System.out.println("Lookup data: " + response.getResult().getData());
        System.out.println("Lookup raw data: " + response.getResult().getRawData());
    }
}
