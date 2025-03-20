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
import java.util.HashMap;

public class App {
	public static void main(String[] args) {
		Config vault_cfg = null;
		try {
			vault_cfg = Config.fromEnvironment(VaultClient.serviceName);
		} catch (ConfigException e) {
			System.out.println(e);
			System.exit(1);
		}

		VaultClient vault = new VaultClient.Builder(vault_cfg).build();
		String auditTokenVaultId = System.getenv("PANGEA_AUDIT_TOKEN_VAULT_ID");
		final var filter = new HashMap<String, String>();
		filter.put("id", auditTokenVaultId);
		ListResponse getResponse = null;
		try {
			getResponse = vault.getBulk(
					GetBulkRequest.builder().filter(filter).build());
		} catch (PangeaException e) {
			System.out.println(e);
			System.exit(1);
		} catch (PangeaAPIException e) {
			System.out.println(e);
			System.exit(1);
		}
		String auditToken = getResponse.getResult().getItems().get(0).getItemVersions().get(0).getToken();

		String urlTemplate = System.getenv("PANGEA_URL_TEMPLATE");
		Config auditCfg = Config.builder().token(auditToken).baseURLTemplate(urlTemplate).customUserAgent("test").build();
		AuditClient audit = new AuditClient.Builder(auditCfg).build();
		StandardEvent event1 = new StandardEvent.Builder("Sign up")
				.actor("java-sdk")
				.build();

		try {
			LogBulkResponse response = audit.logBulkAsync(new IEvent[] { event1 }, new LogConfig.Builder().build());
			System.out.printf("Success. request_id: %s\n", response.getRequestId());
		} catch (Exception e) {
			System.out.println("Failed to send logs: " + e);
			System.exit(1);
		}

	}
}
