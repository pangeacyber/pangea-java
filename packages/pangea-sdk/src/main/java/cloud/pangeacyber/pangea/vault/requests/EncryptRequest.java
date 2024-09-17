package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
public class EncryptRequest extends BaseRequest {

	/** The item ID. */
	@NonNull
	@JsonProperty("id")
	String id;

	/** A message to be encrypted (Base64 encoded). */
	@NonNull
	@JsonProperty("plain_text")
	String plainText;

	/** The item version. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	Integer version;

	/** User provided authentication data. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("additional_data")
	String additionalData;
}
