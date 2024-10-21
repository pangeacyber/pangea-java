package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.AsymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class AsymmetricGenerateResult extends CommonGenerateResult {

	/** Type of the Vault item. */
	@NonNull
	@JsonProperty("type")
	ItemType type = ItemType.ASYMMETRIC_KEY;

	/** Algorithm of the key. */
	@JsonProperty("algorithm")
	AsymmetricAlgorithm algorithm;

	/** Purpose of the key. */
	@JsonProperty("purpose")
	KeyPurpose purpose;
}
