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

	// TODO: Doc
	public UserProfileGetResponse getByEmail(String email) throws PangeaException, PangeaAPIException {
		UserProfileGetRequest request = new UserProfileGetRequest(email, null);
		return post("/v1/user/profile/get", request, UserProfileGetResponse.class);
	}

	// TODO: Doc
	public UserProfileGetResponse getByID(String id) throws PangeaException, PangeaAPIException {
		UserProfileGetRequest request = new UserProfileGetRequest(null, id);
		return post("/v1/user/profile/get", request, UserProfileGetResponse.class);
	}

	// TODO: Doc
	public UserProfileUpdateResponse update(UserProfileUpdateRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/user/profile/update", request, UserProfileUpdateResponse.class);
	}
}
