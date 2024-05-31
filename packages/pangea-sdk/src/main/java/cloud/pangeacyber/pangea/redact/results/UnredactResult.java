package cloud.pangeacyber.pangea.redact.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnredactResult {

	@JsonProperty("data")
	Object data;

	public Object getData() {
		return data;
	}
}
