package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
import cloud.pangeacyber.pangea.authn.models.Scopes;
import cloud.pangeacyber.pangea.authn.requests.UserCreateRequest;
import cloud.pangeacyber.pangea.authn.requests.UserInviteRequest;
import cloud.pangeacyber.pangea.authn.requests.UserUpdateRequest;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserDeleteRequest {

	@JsonProperty("email")
	String email;

	public UserDeleteRequest(String email) {
		this.email = email;
	}
}

final class UserListRequest {

	@JsonProperty("scopes")
	Scopes scopes;

	@JsonProperty("glob_scopes")
	Scopes globScopes;

	public UserListRequest(Scopes scopes, Scopes globScopes) {
		this.scopes = scopes;
		this.globScopes = globScopes;
	}
}

final class UserVerifyRequest {

	@JsonProperty("id_provider")
	IDProvider idProvider;

	@JsonProperty("email")
	String email;

	@JsonProperty("authenticator")
	String authenticator;

	public UserVerifyRequest(IDProvider idProvider, String email, String authenticator) {
		this.idProvider = idProvider;
		this.email = email;
		this.authenticator = authenticator;
	}
}

public class User extends Client {

	public static final String serviceName = "authn";
	private UserProfile profile;
	private UserInvite invite;
	private UserMFA mfa;
	private UserLogin login;

	public User(Config config) {
		super(config, serviceName);
		profile = new UserProfile(config);
		invite = new UserInvite(config);
		mfa = new UserMFA(config);
		login = new UserLogin(config);
	}

	// TODO: Doc
	public UserCreateResponse create(UserCreateRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/user/create", request, UserCreateResponse.class);
	}

	// TODO: Doc
	public UserDeleteResponse delete(String email) throws PangeaException, PangeaAPIException {
		UserDeleteRequest request = new UserDeleteRequest(email);
		return doPost("/v1/user/delete", request, UserDeleteResponse.class);
	}

	// TODO: Doc
	public UserUpdateResponse update(UserUpdateRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/user/update", request, UserUpdateResponse.class);
	}

	// TODO: Doc
	public UserInviteResponse invite(UserInviteRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/user/invite", request, UserInviteResponse.class);
	}

	// TODO: Doc
	public UserListResponse list(Scopes scopes, Scopes globScopes) throws PangeaException, PangeaAPIException {
		UserListRequest request = new UserListRequest(scopes, globScopes);
		return doPost("/v1/user/list", request, UserListResponse.class);
	}

	// TODO: Doc
	public UserVerifyResponse verify(IDProvider idProvider, String email, String authenticator)
		throws PangeaException, PangeaAPIException {
		UserVerifyRequest request = new UserVerifyRequest(idProvider, email, authenticator);
		return doPost("/v1/user/verify", request, UserVerifyResponse.class);
	}

	public UserProfile profile() {
		return profile;
	}

	public UserInvite invite() {
		return invite;
	}

	public UserMFA mfa() {
		return mfa;
	}

	public UserLogin login() {
		return login;
	}
}
