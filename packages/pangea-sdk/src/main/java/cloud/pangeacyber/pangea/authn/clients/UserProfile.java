package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.UserProfileUpdateRequest;
import cloud.pangeacyber.pangea.authn.responses.UserProfileGetResponse;
import cloud.pangeacyber.pangea.authn.responses.UserProfileUpdateResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserProfileGetRequest extends BaseRequest {

	/** An email address. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	/** The identity of a user or a service. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	/** A username. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("username")
	String username;

	public UserProfileGetRequest(String email, String id, String username) {
		this.email = email;
		this.id = id;
		this.username = username;
	}
}

public class UserProfile extends AuthNBaseClient {

	public UserProfile(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Get user - email
	 * @pangea.description Get user's information by email.
	 * @pangea.operationId authn_post_v2_user_profile_get 1
	 * @param email
	 * @return UserProfileGetResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserProfileGetResponse response =
	 * 	client.user().profile().getByEmail("joe.user@email.com");
	 * }
	 */
	public UserProfileGetResponse getByEmail(String email) throws PangeaException, PangeaAPIException {
		UserProfileGetRequest request = new UserProfileGetRequest(email, null, null);
		return post("/v2/user/profile/get", request, UserProfileGetResponse.class);
	}

	/**
	 * Get user - id
	 * @pangea.description Get user's information by id.
	 * @pangea.operationId authn_post_v2_user_profile_get 2
	 * @param id The identity of a user or a service
	 * @return UserProfileGetResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserProfileGetResponse response =
	 * 	client.user().profile().getByID("pui_xpkhwpnz2cmegsws737xbsqnmnuwtbm5");
	 * }
	 */
	public UserProfileGetResponse getByID(String id) throws PangeaException, PangeaAPIException {
		UserProfileGetRequest request = new UserProfileGetRequest(null, id, null);
		return post("/v2/user/profile/get", request, UserProfileGetResponse.class);
	}

	/**
	 * Get user - username
	 * @pangea.description Get user's information by username.
	 * @pangea.operationId authn_post_v2_user_profile_get 3
	 * @param username A username.
	 * @return User's profile.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var response = client.user().profile().getByUsername("foobar");
	 * }
	 */
	public UserProfileGetResponse getByUsername(String username) throws PangeaException, PangeaAPIException {
		return post(
			"/v2/user/profile/get",
			new UserProfileGetRequest(null, null, username),
			UserProfileGetResponse.class
		);
	}

	/**
	 * Update user
	 * @pangea.description Update user's information by identity or email.
	 * @pangea.operationId authn_post_v2_user_profile_update
	 * @param request
	 * @return UserProfileUpdateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * Profile updatedProfile = new Profile();
	 * updatedProfile.put("country", "Argentina");
	 *
	 * UserProfileUpdateRequest request =
	 * 	new UserProfileUpdateRequest
	 * 		.Builder(updatedProfile)
	 * 		.setID("pui_xpkhwpnz2cmegsws737xbsqnmnuwtbm5")
	 * 		.build();
	 *
	 * UserProfileUpdateResponse response =
	 * 	client.user().profile().update(request);
	 * }
	 */
	public UserProfileUpdateResponse update(UserProfileUpdateRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/user/profile/update", request, UserProfileUpdateResponse.class);
	}
}
