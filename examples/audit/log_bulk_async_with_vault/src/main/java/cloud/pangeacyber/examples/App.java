package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.vault.VaultClient;
import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.models.StandardEvent;
import cloud.pangeacyber.pangea.audit.models.IEvent;
import cloud.pangeacyber.pangea.audit.models.LogConfig;
import cloud.pangeacyber.pangea.audit.responses.LogBulkResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.responses.*;

public class App
{
    public static void main( String[] args )
    {
        Config vault_cfg = null;
        try {
            vault_cfg = Config.fromEnvironment(VaultClient.serviceName);
        } catch (ConfigException e){
            System.out.println(e);
            System.exit(1);
        }

        VaultClient vault = new VaultClient.Builder(vault_cfg).build();
        String auditTokenVaultId = System.getenv("PANGEA_AUDIT_TOKEN_VAULT_ID");
        GetResponse getResponse = null;
        try {
            getResponse = vault.get(
                    new GetRequest.Builder(auditTokenVaultId).build()
            );
        } catch (PangeaException e) {
            System.out.println(e);
            System.exit(1);
        } catch (PangeaAPIException e) {
            System.out.println(e);
            System.exit(1);
        }
        String auditToken = getResponse.getResult().getCurrentVersion().getSecret();


        String domain = System.getenv("PANGEA_DOMAIN");
        Config audit_cfg = new Config.Builder(auditToken, domain).customUserAgent("test").build();
        AuditClient audit = new AuditClient.Builder(audit_cfg).build();
        StandardEvent event1 = new StandardEvent.Builder("Sign up")
                        .actor("java-sdk")
                        .build();

        try {
            LogBulkResponse response = audit.logBulkAsync(new IEvent[]{event1}, new LogConfig.Builder().build());
            System.out.printf("Success. request_id: %s\n", response.getRequestId());
        } catch (Exception e){
            System.out.println("Failed to send logs: " + e);
            System.exit(1);
        }

    }
}
