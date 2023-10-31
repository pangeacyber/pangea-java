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

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	public UserProfileGetRequest(String email, String id) {
		this.email = email;
		this.id = id;
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
		UserProfileGetRequest request = new UserProfileGetRequest(email, null);
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
		UserProfileGetRequest request = new UserProfileGetRequest(null, id);
		return post("/v2/user/profile/get", request, UserProfileGetResponse.class);
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
