package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class AsymmetricStoreResult extends CommonStoreResult {

	@JsonProperty("algorithm")
	String algorithm;

	@JsonProperty("purpose")
	String purpose;

	@JsonProperty("public_key")
	String encodedPublicKey;
}
