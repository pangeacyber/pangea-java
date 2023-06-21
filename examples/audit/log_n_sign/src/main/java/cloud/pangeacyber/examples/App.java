package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.models.Event;
import cloud.pangeacyber.pangea.audit.models.LogConfig;
import cloud.pangeacyber.pangea.audit.responses.LogResponse;
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

        AuditClient client = new AuditClient.Builder(cfg)
                                .withPrivateKey("./src/main/java/cloud/pangeacyber/examples/privkey")
                                .build();
        Event event = new Event.Builder("This is a message to log")
                        .action("Login")
                        .actor("Terminal")
                        .build();
        LogResponse response = null;
        try {
            response = client.log(
                event,
                Event.class,
                new LogConfig.Builder()
                    .signLocal(true)
                    .verbose(true)
                    .verify(true)
                    .build()
            );
        } catch (Exception e){
            System.out.println("Fail to perfom log: " + e);
            System.exit(1);
        }

        System.out.println("Log success");
        System.out.println("Hash: " + response.getResult().getHash());
        Event eventResult = (Event)response.getResult().getEventEnvelope().getEvent();
        System.out.println("Message: " + eventResult.getMessage());
        System.out.println("Signature verification: " + response.getResult().getSignatureVerification());
    }
}
