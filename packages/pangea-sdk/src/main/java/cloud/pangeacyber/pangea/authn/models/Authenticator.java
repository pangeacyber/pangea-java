package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Authenticator {

	@JsonProperty("id")
	private String id;

	@JsonProperty("type")
	private String type;

	@JsonProperty("enable")
	private boolean enable;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("provider")
	private String provider;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("rpid")
	private String rpid;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("phase")
	private String phase;

	public Authenticator() {}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public boolean isEnable() {
		return enable;
	}

	public String getProvider() {
		return provider;
	}

	public String getRpid() {
		return rpid;
	}

	public String getPhase() {
		return phase;
	}
}
