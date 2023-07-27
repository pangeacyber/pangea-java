package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderCreateResult {

	@JsonProperty("id")
	String id = null;

	public FolderCreateResult() {}

	public String getId() {
		return id;
	}
}
