package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.LogResult;
import cloud.pangeacyber.pangea.exceptions.AcceptedRequestException;
import cloud.pangeacyber.pangea.audit.models.StandardEvent;
import cloud.pangeacyber.pangea.audit.models.IEvent;
import cloud.pangeacyber.pangea.audit.models.LogConfig;
import cloud.pangeacyber.pangea.audit.responses.LogBulkResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;

public class App
{
    public static void main( String[] args )
    {
        System.out.println("Sending multiple events");
        Config cfg = null;
        try {
            cfg = Config.fromEnvironment(AuditClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        AuditClient client = new AuditClient.Builder(cfg).build();
        StandardEvent event1 = new StandardEvent.Builder("Sign up")
                        .actor("java-sdk")
                        .build();

        StandardEvent event2 = new StandardEvent.Builder("Sign in")
                        .actor("java-sdk")
                        .build();

        try {
            client.logBulkAsync(new IEvent[]{event1, event2}, new LogConfig.Builder().build());
        } catch (AcceptedRequestException e){
            System.out.println("AcceptedRequestException as expected");
        } catch (Exception e){
            System.out.println("Fail to perfom log: " + e);
            System.exit(1);
        }

    }
}
