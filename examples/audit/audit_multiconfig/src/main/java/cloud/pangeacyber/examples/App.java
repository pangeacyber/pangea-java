package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.models.LogConfig;
import cloud.pangeacyber.pangea.audit.models.StandardEvent;
import cloud.pangeacyber.pangea.audit.responses.LogResponse;

public class App {
    public static void main(String[] args) {
        String token = System.getenv("PANGEA_AUDIT_MULTICONFIG_TOKEN");
        String urlTemplate = System.getenv("PANGEA_URL_TEMPLATE");
        String configId = System.getenv("PANGEA_AUDIT_CONFIG_ID");

        Config cfg = Config.builder().token(token).baseURLTemplate(urlTemplate).build();

        // Set config_id in service builder
        AuditClient client = new AuditClient.Builder(cfg).withConfigID(configId).build();
        StandardEvent event =
                new StandardEvent.Builder("Hello, World!")
                        .action("Login")
                        .actor("Terminal")
                        .build();
        LogResponse response = null;
        try {
            response = client.log(event, new LogConfig.Builder().build());
        } catch (Exception e) {
            System.out.println("Failed to perform log: " + e);
            System.exit(1);
        }

        System.out.println("Logging: " + event.getMessage());
        System.out.println("Response: " + response.getResult().getHash());
    }
}
