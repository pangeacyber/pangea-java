package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.TransformAlphabet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * Parameters for a decrypt transform request.
 */
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
public class DecryptTransformRequest extends BaseRequest {

	/** The ID of the key to use. */
	@NonNull
	@JsonProperty("id")
	private String id;

	/** A message encrypted by Vault. */
	@NonNull
	@JsonProperty("cipher_text")
	private String cipherText;

	/**
	 * User provided tweak string. If not provided, a random string will be
	 * generated and returned. The user must securely store the tweak source
	 * which will be needed to decrypt the data.
	 */
	@NonNull
	@JsonProperty("tweak")
	private String tweak;

	/** Set of characters to use for format-preserving encryption (FPE). */
	@NonNull
	@JsonProperty("alphabet")
	private TransformAlphabet alphabet;

	/** The item version. Defaults to the current version. */
	@JsonProperty("version")
	@JsonInclude(Include.NON_NULL)
	private Integer version;
}
