package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.responses.UserLoginResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserLoginPasswordRequest {

	@JsonProperty("email")
	String email;

	@JsonProperty("password")
	String password;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("extra_profile")
	Profile extraProfile;

	public UserLoginPasswordRequest(String email, String password, Profile extraProfile) {
		this.email = email;
		this.password = password;
		this.extraProfile = extraProfile;
	}
}

final class UserLoginSocialRequest {

	@JsonProperty("provider")
	IDProvider provider;

	@JsonProperty("email")
	String email;

	@JsonProperty("social_id")
	String socialID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("extra_profile")
	Profile extraProfile;

	public UserLoginSocialRequest(IDProvider provider, String email, String socialID, Profile extraProfile) {
		this.provider = provider;
		this.email = email;
		this.socialID = socialID;
		this.extraProfile = extraProfile;
	}
}

public class UserLogin extends Client {

	public static final String serviceName = "authn";

	public UserLogin(Config config) {
		super(config, serviceName);
	}

	// TODO: Doc
	public UserLoginResponse Password(String email, String password) throws PangeaException, PangeaAPIException {
		UserLoginPasswordRequest request = new UserLoginPasswordRequest(email, password, null);
		return doPost("/v1/user/login/password", request, UserLoginResponse.class);
	}

	// TODO: Doc
	public UserLoginResponse Password(String email, String password, Profile extraProfile)
		throws PangeaException, PangeaAPIException {
		UserLoginPasswordRequest request = new UserLoginPasswordRequest(email, password, extraProfile);
		return doPost("/v1/user/login/password", request, UserLoginResponse.class);
	}

	// TODO: Doc
	public UserLoginResponse Social(IDProvider provider, String email, String socialID)
		throws PangeaException, PangeaAPIException {
		UserLoginSocialRequest request = new UserLoginSocialRequest(provider, email, socialID, null);
		return doPost("/v1/user/login/social", request, UserLoginResponse.class);
	}

	// TODO: Doc
	public UserLoginResponse Social(IDProvider provider, String email, String socialID, Profile extraProfile)
		throws PangeaException, PangeaAPIException {
		UserLoginSocialRequest request = new UserLoginSocialRequest(provider, email, socialID, extraProfile);
		return doPost("/v1/user/login/social", request, UserLoginResponse.class);
	}
}
