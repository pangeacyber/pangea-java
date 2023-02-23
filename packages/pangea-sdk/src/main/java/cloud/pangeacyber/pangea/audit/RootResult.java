package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RootResult {

	@JsonProperty("data")
	Root root;

	public Root getRoot() {
		return root;
	}
}
