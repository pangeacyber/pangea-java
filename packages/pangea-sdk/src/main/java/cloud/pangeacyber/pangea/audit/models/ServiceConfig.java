package cloud.pangeacyber.pangea.audit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Configuration options available for audit service. */
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class ServiceConfig {

	/** Version of the service config. */
	int version;

	/** The config ID. */
	String id;

	/** The DB timestamp when this config was created. Ignored when submitted. */
	String createdAt;

	/** The DB timestamp when this config was last updated at. */
	String updatedAt;

	/** Configuration name. */
	String name;

	/** Retention window to store audit logs. */
	String retention;

	/** Retention window for cold query result / state information. */
	String coldQueryResultRetention;

	/** Retention window for logs in cold storage. Deleted afterwards. */
	String coldStorage;

	/** Retention window to keep audit logs in hot storage. */
	String hotStorage;

	/** Length of time to preserve server-side query result caching. */
	String queryResultRetention;

	/** A redact service config that will be used to redact PII from logs. */
	String redactServiceConfigId;

	/** Fields to perform redaction against. */
	List<String> redactionFields;

	/** A vault service config that will be used to sign logs. */
	String vaultServiceConfigId;

	/** ID of the Vault key used for signing. If missing, use a default Audit key. */
	String vaultKeyId;

	/** Enable/disable event signing. */
	Boolean vaultSign;

	/** Audit log field configuration. Only settable at create time. */
	AuditSchema schema;

	/** Configuration for forwarding audit logs to external systems. */
	ForwardingConfiguration forwardingConfiguration;

	/** Retention window for logs in warm storage. Migrated to cold or deleted afterwards. */
	String warmStorage;
}
