package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.ExportEncryptionAlgorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonInclude(Include.NON_NULL)
public final class ExportRequest extends BaseRequest {

	/** The ID of the key to use.*/
	@NonNull
	@JsonProperty("id")
	private String id;

	/** The item version. Defaults to the current version.*/
	@JsonProperty("version")
	private Integer version;

	/**
	 * This is the password that will be used along with a salt to derive the
	 * symmetric key that is used to encrypt the exported key material. Required
	 * if `encryption_type` is `kem`.
	 */
	@JsonProperty("kem_password")
	private String kemPassword;

	/** Public key in pem format used to encrypt exported key(s). */
	@JsonProperty("asymmetric_public_key")
	private String asymmetricPublicKey;

	/** The algorithm of the public key. */
	@JsonProperty("asymmetric_algorithm")
	private ExportEncryptionAlgorithm asymmetricAlgorithm;
}
