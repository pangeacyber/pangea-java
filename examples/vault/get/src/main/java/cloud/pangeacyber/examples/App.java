package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.vault.VaultClient;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.vault.requests.*;
import java.util.HashMap;
import cloud.pangeacyber.pangea.audit.AuditClient;
import cloud.pangeacyber.pangea.audit.models.*;
import cloud.pangeacyber.pangea.audit.responses.*;
import cloud.pangeacyber.pangea.Config;

public class App {
	public static void main(String[] args) {
		Config cfg = null;
		try {
			cfg = Config.fromEnvironment(VaultClient.serviceName);
		} catch (ConfigException e) {
			System.out.println(e);
			System.exit(1);
		}

		VaultClient vaultClient = new VaultClient.Builder(cfg).build();
		String auditTokenId = System.getenv("PANGEA_AUDIT_TOKEN_VAULT_ID");
		if (auditTokenId == null || auditTokenId.isEmpty()) {
			System.out.println("Missing PANGEA_AUDIT_TOKEN_ID");
			System.exit(1);
		}

		try {
			System.out.println("Fetch the audit token...");
			final var filter = new HashMap<String, String>();
			filter.put("id", auditTokenId);
			final var getResponse = vaultClient.getBulk(GetBulkRequest.builder().filter(filter).build());

			String auditToken = getResponse.getResult().getItems().get(0).getItemVersions().get(0).getToken();

			if (auditToken == null || auditToken.isEmpty()) {
				System.out.println("Unexpected: Failed to get token value");
				System.exit(1);
			}

			System.out.println("Create Audit client...");
			Config auditCfg = new Config.Builder(auditToken, cfg.getDomain()).build();
			AuditClient auditClient = new AuditClient.Builder(auditCfg).build();
			StandardEvent event = new StandardEvent.Builder("Hello, World!").build();
			LogResponse response = null;
			System.out.println("Logging an event...");
			try {
				response = auditClient.log(event, new LogConfig.Builder().build());
			} catch (Exception e) {
				System.out.println("Failed to perform log: " + e);
				System.exit(1);
			}

			System.out.println("Success...");
		} catch (Exception e) {
			System.out.println("Error: " + e);
			System.exit(1);
		}

	}
}
