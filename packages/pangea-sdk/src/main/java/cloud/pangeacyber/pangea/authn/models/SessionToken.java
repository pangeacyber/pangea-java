package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionToken {

	@JsonProperty("id")
	String id;

	@JsonProperty("type")
	String type;

	@JsonProperty("life")
	int life;

	@JsonProperty("expire")
	String expire;

	@JsonProperty("identity")
	String identity;

	@JsonProperty("email")
	String email;

	@JsonProperty("scopes")
	Scopes scopes;

	@JsonProperty("profile")
	Profile profile;

	@JsonProperty("created_at")
	String createdAt;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("intelligence")
	Intelligence intelligence;

	public String getID() {
		return id;
	}

	public String getType() {
		return type;
	}

	public int getLife() {
		return life;
	}

	public String getExpire() {
		return expire;
	}

	public String getEmail() {
		return email;
	}

	public Profile getProfile() {
		return profile;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public Scopes getScopes() {
		return scopes;
	}

	public String getIdentity() {
		return identity;
	}
}
