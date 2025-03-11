package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class VaultParameters {

	/**
	 * A vault key ID of an exportable key used to redact with FPE instead of
	 * using the service config default.
	 */
	@JsonProperty("fpe_key_id")
	private String fpeKeyId;

	/**
	 * A vault secret ID of a secret used to salt a hash instead of using the
	 * service config default.
	 */
	@JsonProperty("salt_secret_id")
	private String saltSecretId;
}
