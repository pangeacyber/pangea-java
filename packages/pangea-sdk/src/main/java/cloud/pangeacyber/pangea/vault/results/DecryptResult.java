package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class DecryptResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("plain_text")
	String plainText;

	@JsonProperty("version")
	Integer version;

	@JsonProperty("algorithm")
	String algorithm;
}
