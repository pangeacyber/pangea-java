package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode
@Data
@Jacksonized
@SuperBuilder
@Value
public class VaultParameters {

	/** A vault key ID of an exportable key used to redact with FPE instead of using the service config default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("fpe_key_id")
	private String FpeKeyId = null;

	/** A vault secret ID of a secret used to salt a hash instead of using the service config default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("salt_secret_id")
	private String saltSecretId = null;
}
