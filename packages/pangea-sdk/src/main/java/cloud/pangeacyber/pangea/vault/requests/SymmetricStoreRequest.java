package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@Value
public class SymmetricStoreRequest extends CommonStoreRequest {

	@JsonProperty("type")
	static final ItemType type = ItemType.SYMMETRIC_KEY;

	/** The purpose of this key. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("purpose")
	KeyPurpose purpose = KeyPurpose.ENCRYPTION;

	/** The algorithm of the key. */
	@NonNull
	@JsonProperty("algorithm")
	SymmetricAlgorithm algorithm;

	/** The key material */
	@NonNull
	@JsonProperty("key")
	String key;

	/** Whether the key is exportable or not. */
	@Builder.Default
	@JsonProperty("exportable")
	boolean exportable = false;
}
