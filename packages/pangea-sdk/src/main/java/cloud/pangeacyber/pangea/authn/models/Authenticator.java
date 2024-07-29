package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Authenticator {

	/** An ID for an authenticator. */
	@JsonProperty("id")
	private String id;

	/** An authentication mechanism. */
	@JsonProperty("type")
	private String type;

	/** Provider. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("provider")
	private String provider;

	/** Provider name. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("provider_name")
	private String providerName;

	/** RPID. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("rpid")
	private String rpid;

	/** Enabled. */
	@JsonProperty("enabled")
	private boolean enabled;

	/** Phase. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("phase")
	private String phase;

	/** Enrolling browser. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("enrolling_browser")
	private String enrollingBrowser;

	/** Enrolling IP. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("enrolling_ip")
	private String enrollingIp;

	/** A time in ISO-8601 format. */
	@JsonProperty("created_at")
	private String createdAt;

	/** A time in ISO-8601 format. */
	@JsonProperty("updated_at")
	private String updatedAt;

	public Authenticator() {}

	/** An ID for an authenticator. */
	public String getId() {
		return id;
	}

	/** An authentication mechanism. */
	public String getType() {
		return type;
	}

	/** Provider. */
	public String getProvider() {
		return provider;
	}

	/** Provider name. */
	public String getProviderName() {
		return providerName;
	}

	/** RPID. */
	public String getRpid() {
		return rpid;
	}

	/** Enabled. */
	public boolean isEnabled() {
		return enabled;
	}

	/** Phase. */
	public String getPhase() {
		return phase;
	}

	/** Enrolling browser. */
	public String getEnrollingBrowser() {
		return enrollingBrowser;
	}

	/** Enrolling IP. */
	public String getEnrollingIp() {
		return enrollingIp;
	}

	/** A time in ISO-8601 format. */
	public String getCreatedAt() {
		return createdAt;
	}

	/** A time in ISO-8601 format. */
	public String getUpdatedAt() {
		return updatedAt;
	}
}
