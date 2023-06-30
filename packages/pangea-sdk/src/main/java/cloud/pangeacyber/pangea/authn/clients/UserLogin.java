package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.responses.UserLoginResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserLoginPasswordRequest extends BaseRequest {

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

final class UserLoginSocialRequest extends BaseRequest {

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

public class UserLogin extends AuthNBaseClient {

	public UserLogin(AuthNClient.Builder builder) {
		super(builder);
	}

	// TODO: Doc
	public UserLoginResponse Password(String email, String password) throws PangeaException, PangeaAPIException {
		UserLoginPasswordRequest request = new UserLoginPasswordRequest(email, password, null);
		return post("/v1/user/login/password", request, UserLoginResponse.class);
	}

	// TODO: Doc
	public UserLoginResponse Password(String email, String password, Profile extraProfile)
		throws PangeaException, PangeaAPIException {
		UserLoginPasswordRequest request = new UserLoginPasswordRequest(email, password, extraProfile);
		return post("/v1/user/login/password", request, UserLoginResponse.class);
	}

	// TODO: Doc
	public UserLoginResponse Social(IDProvider provider, String email, String socialID)
		throws PangeaException, PangeaAPIException {
		UserLoginSocialRequest request = new UserLoginSocialRequest(provider, email, socialID, null);
		return post("/v1/user/login/social", request, UserLoginResponse.class);
	}

	// TODO: Doc
	public UserLoginResponse Social(IDProvider provider, String email, String socialID, Profile extraProfile)
		throws PangeaException, PangeaAPIException {
		UserLoginSocialRequest request = new UserLoginSocialRequest(provider, email, socialID, extraProfile);
		return post("/v1/user/login/social", request, UserLoginResponse.class);
	}
}
