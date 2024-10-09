package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.ItemVersionState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonInclude(Include.NON_NULL)
@SuperBuilder
@Value
public class KeyRotateRequest extends CommonRotateRequest {

	/** The public key (in PEM format). */
	@JsonProperty("public_key")
	String publicKey;

	/** The private key (in PEM format). */
	@JsonProperty("private_key")
	String privateKey;

	/** The key material. */
	@JsonProperty("key")
	String key;
}
