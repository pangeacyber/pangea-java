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
	@JsonProperty("identity")
	String identity;

	public UserProfileGetRequest(String email, String identity) {
		this.email = email;
		this.identity = identity;
	}
}

public class UserProfile extends Client {

	public static final String serviceName = "authn";

	public UserProfile(Config config) {
		super(config, serviceName);
	}

	// TODO: Doc
	public UserProfileGetResponse getWithEmail(String email) throws PangeaException, PangeaAPIException {
		UserProfileGetRequest request = new UserProfileGetRequest(email, null);
		return doPost("/v1/user/profile/get", request, UserProfileGetResponse.class);
	}

	// TODO: Doc
	public UserProfileGetResponse getWithIdentity(String identity) throws PangeaException, PangeaAPIException {
		UserProfileGetRequest request = new UserProfileGetRequest(null, identity);
		return doPost("/v1/user/profile/get", request, UserProfileGetResponse.class);
	}

	// TODO: Doc
	public UserProfileUpdateResponse update(UserProfileUpdateRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/user/profile/update", request, UserProfileUpdateResponse.class);
	}
}
