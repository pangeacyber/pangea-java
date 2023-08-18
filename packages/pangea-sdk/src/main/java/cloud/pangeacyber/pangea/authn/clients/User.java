package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
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

final class UserDeleteByEmailRequest extends BaseRequest {

	@JsonProperty("email")
	String email;

	public UserDeleteByEmailRequest(String email) {
		this.email = email;
	}
}

final class UserDeleteByIDRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	public UserDeleteByIDRequest(String id) {
		this.id = id;
	}
}

final class UserVerifyRequest extends BaseRequest {

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

public class User extends AuthNBaseClient {

	private UserProfile profile;
	private UserInvite invite;
	private UserMFA mfa;
	private UserLogin login;
	private UserPassword password;

	public User(AuthNClient.Builder builder) {
		super(builder);
		profile = new UserProfile(builder);
		invite = new UserInvite(builder);
		mfa = new UserMFA(builder);
		login = new UserLogin(builder);
		password = new UserPassword(builder);
	}

	/**
	 * Create User
	 * @pangea.description Create a user.
	 * @pangea.operationId authn_post_v1_user_create
	 * @param request
	 * @return UserCreateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserCreateResponse response = client.user().create(
	 * 	new UserCreateRequest
	 * 		.Builder(
	 * 			"joe.user@email.com",
	 * 			"My1s+Password",
	 * 			IDProvider.PASSWORD).build());
	 * }
	 */
	public UserCreateResponse create(UserCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/create", request, UserCreateResponse.class);
	}

	/**
	 * Delete User
	 * @pangea.description Delete a user by email address.
	 * @pangea.operationId authn_post_v1_user_delete 1
	 * @param email An email address
	 * @return UserDeleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.user().deleteByEmail("example@example.com");
	 * }
	 */
	public UserDeleteResponse deleteByEmail(String email) throws PangeaException, PangeaAPIException {
		return post("/v1/user/delete", new UserDeleteByEmailRequest(email), UserDeleteResponse.class);
	}

	/**
	 * Delete User
	 * @pangea.description Delete a user by ID.
	 * @pangea.operationId authn_post_v1_user_delete 2
	 * @param id
	 * @return UserDeleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.user().deleteByID("pui_xpkhwpnz2cmegsws737xbsqnmnuwtbm5");
	 * }
	 */
	public UserDeleteResponse deleteByID(String id) throws PangeaException, PangeaAPIException {
		return post("/v1/user/delete", new UserDeleteByIDRequest(id), UserDeleteResponse.class);
	}

	/**
	 * Update user's settings
	 * @pangea.description Update user's settings.
	 * @pangea.operationId authn_post_v1_user_update
	 * @param request
	 * @return UserUpdateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserUpdateResponse response = client.user().update(
	 * 	new UserUpdateRequest
	 * 		.Builder()
	 * 		.setEmail("joe.user@email.com")
	 * 		.setRequireMFA(true).build());
	 * }
	 */
	public UserUpdateResponse update(UserUpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/update", request, UserUpdateResponse.class);
	}

	/**
	 * Invite User
	 * @pangea.description Send an invitation to a user.
	 * @pangea.operationId authn_post_v1_user_invite
	 * @param request
	 * @return UserInviteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserInviteRequest request = new UserInviteRequest
	 * 	.Builder(
	 * 		"admin@email.com",
	 * 		"joe.user@email.com",
	 * 		"/callback",
	 * 		"pcb_zurr3lkcwdp5keq73htsfpcii5k4zgm7")
	 * 	.build();
	 *
	 * UserInviteResponse response = client.user().invite(request);
	 * }
	 */
	public UserInviteResponse invite(UserInviteRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/invite", request, UserInviteResponse.class);
	}

	/**
	 * List Users
	 * @pangea.description Look up users by scopes.
	 * @pangea.operationId authn_post_v1_user_list
	 * @param request
	 * @return UserListResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserListRequest request =
	 * 	new UserListRequest.Builder().build();
	 *
	 * UserListResponse response = client.user().list(request);
	 * }
	 */
	public UserListResponse list(UserListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/list", request, UserListResponse.class);
	}

	/**
	 * Verify User
	 * @pangea.description Verify a user's primary authentication.
	 * @pangea.operationId authn_post_v1_user_verify
	 * @param idProvider
	 * @param email
	 * @param authenticator
	 * @return UserVerifyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserVerifyResponse response = client.user().verify(
	 * 	IDProvider.PASSWORD,
	 * 	"joe.user@email.com",
	 * 	"My1s+Password");
	 * }
	 */
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

	public UserPassword password() {
		return password;
	}
}
