package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.models.StandardEvent;
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

        AuditClient client = new AuditClient.Builder(cfg).build();
        StandardEvent event = new StandardEvent.Builder("Hello, World!")
                        .action("Login")
                        .actor("Terminal")
                        .build();
        LogResponse response = null;
        try {
            response = client.log(event, new LogConfig.Builder().build());
        } catch (Exception e){
            System.out.println("Fail to perfom log: " + e);
            System.exit(1);
        }

        System.out.println("Logging: " + event.getMessage());
        System.out.println("Response: " + response.getResult().getHash());
    }
}
