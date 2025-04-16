package cloud.pangeacyber.pangea.audit.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.audit.models.AuditSchema;
import cloud.pangeacyber.pangea.audit.models.ForwardingConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class CreateServiceConfigRequest extends BaseRequest {

	int version;

	/** Configuration name */
	@NonNull
	String name;

	/** Audit log field configuration. Only settable at create time. */
	AuditSchema schema;

	/** Retention window for logs in cold storage. Deleted afterwards. */
	String coldStorage;

	/** Retention window for logs in hot storage. Migrated to warm, cold, or deleted afterwards. */
	String hotStorage;

	/** Retention window for logs in warm storage. Migrated to cold or deleted afterwards. */
	String warmStorage;

	/** A redact service config that will be used to redact PII from logs. */
	String redactServiceConfigId;

	/** A vault service config that will be used to sign logs. */
	String vaultServiceConfigId;

	/** ID of the Vault key used for signing. If missing, use a default Audit key. */
	String vaultKeyId;

	/** Enable/disable event signing. */
	Boolean vaultSign;

	/** Configuration for forwarding audit logs to external systems. */
	ForwardingConfiguration forwardingConfiguration;
}
