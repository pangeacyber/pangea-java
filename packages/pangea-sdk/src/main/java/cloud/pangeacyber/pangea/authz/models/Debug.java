package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Debug {

	@JsonProperty("path")
	private DebugPath[] path;

	// Empty constructor for Jackson deserialization
	public Debug() {}

	public DebugPath[] getPath() {
		return path;
	}
}
