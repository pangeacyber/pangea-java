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

	/** An email address. */
	@JsonProperty("email")
	String email;

	public UserDeleteByEmailRequest(String email) {
		this.email = email;
	}
}

final class UserDeleteByIDRequest extends BaseRequest {

	/** The id of a user or a service. */
	@JsonProperty("id")
	String id;

	public UserDeleteByIDRequest(String id) {
		this.id = id;
	}
}

final class UserDeleteByUsernameRequest extends BaseRequest {

	/** A username. */
	@JsonProperty("username")
	String username;

	public UserDeleteByUsernameRequest(String username) {
		this.username = username;
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

	/** @hidden */
	public User(AuthNClient.Builder builder) {
		super(builder);
		profile = new UserProfile(builder);
		invite = new UserInvite(builder);
	}

	/**
	 * Create User
	 * @pangea.description Create a user.
	 * @pangea.operationId authn_post_v2_user_create
	 * @param request
	 * @return UserCreateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * Profile profile = new Profile();
	 * profile.put("first_name", "Joe");
	 * profile.put("last_name", "User");
	 *
	 * UserCreateRequest request = new UserCreateRequest.Builder(
	 * 	"joe.user@email.com",
	 * 	profile
	 * ).build();
	 *
	 * UserCreateResponse response = client.user().create(request);
	 * }
	 */
	public UserCreateResponse create(UserCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/user/create", request, UserCreateResponse.class);
	}

	/**
	 * Delete User
	 * @pangea.description Delete a user by email address.
	 * @pangea.operationId authn_post_v2_user_delete 1
	 * @param email An email address
	 * @return UserDeleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.user().deleteByEmail("joe.user@email.com");
	 * }
	 */
	public UserDeleteResponse deleteByEmail(String email) throws PangeaException, PangeaAPIException {
		return post("/v2/user/delete", new UserDeleteByEmailRequest(email), UserDeleteResponse.class);
	}

	/**
	 * Delete User
	 * @pangea.description Delete a user by ID.
	 * @pangea.operationId authn_post_v2_user_delete 2
	 * @param id The identity of a user or a service
	 * @return UserDeleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.user().deleteByID("pui_xpkhwpnz2cmegsws737xbsqnmnuwtbm5");
	 * }
	 */
	public UserDeleteResponse deleteByID(String id) throws PangeaException, PangeaAPIException {
		return post("/v2/user/delete", new UserDeleteByIDRequest(id), UserDeleteResponse.class);
	}

	/**
	 * Delete User
	 * @pangea.description Delete a user by username.
	 * @pangea.operationId authn_post_v2_user_delete 3
	 * @param username A username.
	 * @return An empty object.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * client.user().deleteByUsername("foobar");
	 * }
	 */
	public UserDeleteResponse deleteByUsername(String username) throws PangeaException, PangeaAPIException {
		return post("/v2/user/delete", new UserDeleteByUsernameRequest(username), UserDeleteResponse.class);
	}

	/**
	 * Update user's settings
	 * @pangea.description Update user's settings.
	 * @pangea.operationId authn_post_v2_user_update
	 * @param request
	 * @return UserUpdateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserUpdateRequest request = new UserUpdateRequest.Builder()
	 * 	.setEmail("joe.user@email.com")
	 * 	.setDisabled(true)
	 * 	.build();
	 *
	 * UserUpdateResponse response = client.user().update(request);
	 * }
	 */
	public UserUpdateResponse update(UserUpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/user/update", request, UserUpdateResponse.class);
	}

	/**
	 * Invite User
	 * @pangea.description Send an invitation to a user.
	 * @pangea.operationId authn_post_v2_user_invite
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
	 * 		"https://www.myserver.com/callback",
	 * 		"pcb_zurr3lkcwdp5keq73htsfpcii5k4zgm7")
	 * 	.build();
	 *
	 * UserInviteResponse response = client.user().invite(request);
	 * }
	 */
	public UserInviteResponse invite(UserInviteRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/user/invite", request, UserInviteResponse.class);
	}

	/**
	 * List Users
	 * @pangea.description Look up users by scopes.
	 * @pangea.operationId authn_post_v2_user_list
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
		return post("/v2/user/list", request, UserListResponse.class);
	}

	public UserProfile profile() {
		return profile;
	}

	public UserInvite invite() {
		return invite;
	}
}
