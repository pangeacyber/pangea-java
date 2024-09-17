package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
public class DecryptRequest extends BaseRequest {

	/** The item ID. */
	@NonNull
	@JsonProperty("id")
	String id;

	/** A message encrypted by Vault (Base64 encoded). */
	@NonNull
	@JsonProperty("cipher_text")
	String cipherText;

	/** The item version. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	Integer version = null;

	/** User provided authentication data. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("additional_data")
	String additionalData;
}
