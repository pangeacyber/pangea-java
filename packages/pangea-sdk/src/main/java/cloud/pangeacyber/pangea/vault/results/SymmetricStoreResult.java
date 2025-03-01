package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class SymmetricStoreResult extends CommonStoreResult {

	/** Algorithm of the key. */
	@JsonProperty("algorithm")
	String algorithm;

	/** Purpose of the key. */
	@JsonProperty("purpose")
	KeyPurpose purpose;
}
