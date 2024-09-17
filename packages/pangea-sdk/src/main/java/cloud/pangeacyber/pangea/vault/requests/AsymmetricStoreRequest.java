package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.AsymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@Value
public class AsymmetricStoreRequest extends CommonStoreRequest {

	@NonNull
	@JsonProperty("type")
	final ItemType type = ItemType.ASYMMETRIC_KEY;

	/** The algorithm of the key. */
	@NonNull
	@JsonProperty("algorithm")
	AsymmetricAlgorithm algorithm;

	/** The purpose of this key. */
	@NonNull
	@JsonProperty("purpose")
	KeyPurpose purpose;

	/** The public key (in PEM format). */
	@NonNull
	@JsonProperty("public_key")
	String publicKey;

	/** The private key (in PEM format). */
	@NonNull
	@JsonProperty("private_key")
	String privateKey;

	/** Whether the key is exportable or not. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("exportable")
	boolean exportable;
}
