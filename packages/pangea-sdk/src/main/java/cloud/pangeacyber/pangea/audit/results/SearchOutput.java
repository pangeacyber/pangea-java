package cloud.pangeacyber.pangea.audit.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchOutput extends ResultsOutput {

	@JsonProperty("id")
	String id;

	@JsonProperty("expires_at")
	String expiresAt;

	public String getId() {
		return id;
	}

	public String getExpiresAt() {
		return expiresAt;
	}
}
