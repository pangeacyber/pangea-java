package cloud.pangeacyber.pangea.audit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Configuration for forwarding audit logs to external systems. */
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class ForwardingConfiguration {

	/** Type of forwarding configuration. */
	@NonNull
	String type;

	/** Whether forwarding is enabled. */
	Boolean forwardingEnabled;

	/** URL where events will be written to. Must use HTTPS. */
	String eventUrl;

	/** If indexer acknowledgement is required, this must be provided along with a 'channelId'. */
	String ackUrl;

	/** An optional splunk channel included in each request if indexer acknowledgement is required. */
	String channelId;

	/** Public certificate if a self signed TLS cert is being used. */
	String publicCert;

	/** Optional splunk index passed in the record bodies. */
	String index;

	/** The vault config used to store the HEC token. */
	String vaultConfigId;

	/** The secret ID where the HEC token is stored in vault. */
	String vaultSecretId;
}
