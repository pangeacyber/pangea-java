package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.requests.UserProfileUpdateRequest;
import cloud.pangeacyber.pangea.authn.responses.UserProfileGetResponse;
import cloud.pangeacyber.pangea.authn.responses.UserProfileUpdateResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserProfileGetRequest {

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

public class UserProfile extends Client {

	public static final String serviceName = "authn";

	public UserProfile(Config config) {
		super(config, serviceName);
	}

	// TODO: Doc
	public UserProfileGetResponse getByEmail(String email) throws PangeaException, PangeaAPIException {
		UserProfileGetRequest request = new UserProfileGetRequest(email, null);
		return doPost("/v1/user/profile/get", request, UserProfileGetResponse.class);
	}

	// TODO: Doc
	public UserProfileGetResponse getByID(String id) throws PangeaException, PangeaAPIException {
		UserProfileGetRequest request = new UserProfileGetRequest(null, id);
		return doPost("/v1/user/profile/get", request, UserProfileGetResponse.class);
	}

	// TODO: Doc
	public UserProfileUpdateResponse update(UserProfileUpdateRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/user/profile/update", request, UserProfileUpdateResponse.class);
	}
}
