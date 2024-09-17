package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class EncryptResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("version")
	Integer version;

	@JsonProperty("cipher_text")
	String cipherText;

	@JsonProperty("algorithm")
	String algorithm;
}
