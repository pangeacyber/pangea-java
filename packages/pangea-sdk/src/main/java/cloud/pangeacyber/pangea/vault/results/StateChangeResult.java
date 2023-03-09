package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StateChangeResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("state")
	String state;

	@JsonProperty("version")
	int version;

	@JsonProperty("destroy_at")
	String destroyAt;

	public StateChangeResult() {}

	public String getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public int getVersion() {
		return version;
	}

	public String getDestroyAt() {
		return destroyAt;
	}
}
