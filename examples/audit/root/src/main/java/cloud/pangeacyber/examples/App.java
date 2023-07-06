package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.responses.RootResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(AuditClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        AuditClient client = new AuditClient.Builder(cfg).build();
        RootResponse response = null;
        try {
            response = client.getRoot();
        } catch (Exception e){
            System.out.println("Fail to perfom root request: " + e);
            System.exit(1);
        }

        System.out.println("Root request success");
        System.out.println("Tree size: " + response.getResult().getRoot().getSize());
    }
}
