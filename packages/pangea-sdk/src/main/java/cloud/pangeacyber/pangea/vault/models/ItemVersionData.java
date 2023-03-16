package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemVersionData {

	@JsonProperty("version")
	Integer version;

	@JsonProperty("state")
	String State = null;

	@JsonProperty("created_at")
	String createdAt = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("destroy_at")
	String destroyAt = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("secret")
	String secret = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("public_key")
	String encodedPublicKey = null;

	public ItemVersionData() {}

	public Integer getVersion() {
		return version;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getState() {
		return State;
	}

	public String getSecret() {
		return secret;
	}

	public String getEncodedPublicKey() {
		return encodedPublicKey;
	}

	public String getDestroyedAt() {
		return destroyAt;
	}
}
