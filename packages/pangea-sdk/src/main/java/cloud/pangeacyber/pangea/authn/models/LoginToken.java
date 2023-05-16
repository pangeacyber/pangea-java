package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginToken {

	@JsonProperty("token")
	String token;

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

	@JsonProperty("profile")
	Profile profile;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("scopes")
	Scopes scopes;

	@JsonProperty("created_at")
	String createdAt;

	public String getToken() {
		return token;
	}

	public String getId() {
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

	public String getIdentity() {
		return identity;
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
}
