package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.Event;
import cloud.pangeacyber.pangea.audit.LogResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.audit.SignMode;

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

        AuditClient client = new AuditClient(cfg, "./src/main/java/cloud/pangeacyber/examples/privkey");
        Event event = new Event("This is a message to log");
        event.setAction("Login");
        event.setActor("Terminal");
        LogResponse response = null;
        try {
            response = client.log(event, SignMode.LOCAL, true, true);
        } catch (Exception e){
            System.out.println("Fail to perfom log: " + e);
            System.exit(1);
        }

        System.out.println("Log success");
        System.out.println("Hash: " + response.getResult().getHash());
        System.out.println("Message: " + response.getResult().getEventEnvelope().getEvent().getMessage());
        System.out.println("Signature verification: " + response.getResult().getSignatureVerification());
    }
}
