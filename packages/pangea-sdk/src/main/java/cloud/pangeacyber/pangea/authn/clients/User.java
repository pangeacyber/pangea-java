package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
import cloud.pangeacyber.pangea.authn.requests.UserCreateRequest;
import cloud.pangeacyber.pangea.authn.requests.UserInviteRequest;
import cloud.pangeacyber.pangea.authn.requests.UserListRequest;
import cloud.pangeacyber.pangea.authn.requests.UserUpdateRequest;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserDeleteByEmailRequest {

	@JsonProperty("email")
	String email;

	public UserDeleteByEmailRequest(String email) {
		this.email = email;
	}
}

final class UserDeleteByIDRequest {

	@JsonProperty("id")
	String id;

	public UserDeleteByIDRequest(String id) {
		this.id = id;
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

public class User extends BaseClient {

	private UserProfile profile;
	private UserInvite invite;
	private UserMFA mfa;
	private UserLogin login;

	public User(AuthNClient.Builder builder) {
		super(builder);
		profile = new UserProfile(builder);
		invite = new UserInvite(builder);
		mfa = new UserMFA(builder);
		login = new UserLogin(builder);
	}

	// TODO: Doc
	public UserCreateResponse create(UserCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/create", request, UserCreateResponse.class);
	}

	// TODO: Doc
	public UserDeleteResponse deleteByEmail(String email) throws PangeaException, PangeaAPIException {
		return post("/v1/user/delete", new UserDeleteByEmailRequest(email), UserDeleteResponse.class);
	}

	public UserDeleteResponse deleteByID(String id) throws PangeaException, PangeaAPIException {
		return post("/v1/user/delete", new UserDeleteByIDRequest(id), UserDeleteResponse.class);
	}

	// TODO: Doc
	public UserUpdateResponse update(UserUpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/update", request, UserUpdateResponse.class);
	}

	// TODO: Doc
	public UserInviteResponse invite(UserInviteRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/invite", request, UserInviteResponse.class);
	}

	// TODO: Doc
	public UserListResponse list(UserListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/list", request, UserListResponse.class);
	}

	// TODO: Doc
	public UserVerifyResponse verify(IDProvider idProvider, String email, String authenticator)
		throws PangeaException, PangeaAPIException {
		UserVerifyRequest request = new UserVerifyRequest(idProvider, email, authenticator);
		return post("/v1/user/verify", request, UserVerifyResponse.class);
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
