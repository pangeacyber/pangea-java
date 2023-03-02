package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteResult {

	@JsonProperty("id")
	String id;

	public DeleteResult() {}

	public String getId() {
		return id;
	}
}
