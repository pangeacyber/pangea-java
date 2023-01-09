package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.Event;
import cloud.pangeacyber.pangea.audit.LogResponse;
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

        AuditClient client = new AuditClient(cfg);
        Event event = new Event("Hello, World!");
        event.setAction("Login");
        event.setActor("Terminal");
        LogResponse response = null;
        try {
            response = client.log(event);
        } catch (Exception e){
            System.out.println("Fail to perfom log: " + e);
            System.exit(1);
        }

        System.out.println("Logging: " + event.getMessage());
        System.out.println("Response: " + response.getResult().getHash());
    }
}
