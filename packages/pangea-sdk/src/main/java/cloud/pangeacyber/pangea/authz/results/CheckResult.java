package cloud.pangeacyber.pangea.authz.results;

import cloud.pangeacyber.pangea.authz.models.Debug;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckResult {

	@JsonProperty("schema_id")
	private String schemaID;

	@JsonProperty("schema_version")
	private int schemaVersion;

	@JsonProperty("depth")
	private int depth;

	@JsonProperty("allowed")
	private boolean allowed;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("debug")
	private Debug debug;

	public CheckResult() {}

	public String getSchemaID() {
		return schemaID;
	}

	public int getSchemaVersion() {
		return schemaVersion;
	}

	public int getDepth() {
		return depth;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public Debug getDebug() {
		return debug;
	}
}
