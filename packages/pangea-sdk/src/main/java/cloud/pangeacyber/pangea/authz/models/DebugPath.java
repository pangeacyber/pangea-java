package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DebugPath {

	@JsonProperty("type")
	private String type;

	@JsonProperty("id")
	private String id;

	@JsonProperty("action")
	private String action;

	// Empty constructor for Jackson deserialization
	public DebugPath() {}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getAction() {
		return action;
	}
}
