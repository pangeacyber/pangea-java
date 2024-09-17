package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.JWK;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class JWKGetResult {

	@JsonProperty("keys")
	List<JWK> keys;
}
