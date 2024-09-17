package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.ItemType;
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
public class SymmetricGenerateResult extends CommonGenerateResult {

	@NonNull
	@JsonProperty("type")
	ItemType type = ItemType.SYMMETRIC_KEY;

	@JsonProperty("algorithm")
	String algorithm;

	@JsonProperty("purpose")
	String purpose;
}
