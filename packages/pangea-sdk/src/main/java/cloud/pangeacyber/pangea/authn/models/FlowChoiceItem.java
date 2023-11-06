package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowChoiceItem {

	@JsonProperty("choice")
	private String choice;

	@JsonProperty("data")
	private HashMap<String, Object> data = null;

	public FlowChoiceItem() {}

	public String getChoice() {
		return choice;
	}

	public HashMap<String, Object> getData() {
		return data;
	}
}
