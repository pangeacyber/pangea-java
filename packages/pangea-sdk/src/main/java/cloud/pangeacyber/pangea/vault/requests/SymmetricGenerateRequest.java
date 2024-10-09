package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
public class SymmetricGenerateRequest extends CommonGenerateRequest {

	@NonNull
	@JsonProperty("type")
	final ItemType type = ItemType.SYMMETRIC_KEY;

	/** The algorithm of the key. */
	@NonNull
	@JsonProperty("algorithm")
	SymmetricAlgorithm algorithm;

	/** The purpose of this key. */
	@NonNull
	@JsonProperty("purpose")
	KeyPurpose purpose;
}
