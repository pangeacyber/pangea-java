package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.UserInviteListRequest;
import cloud.pangeacyber.pangea.authn.responses.UserInviteDeleteResponse;
import cloud.pangeacyber.pangea.authn.responses.UserInviteListResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserInviteDeleteRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	public UserInviteDeleteRequest(String id) {
		this.id = id;
	}
}

public class UserInvite extends AuthNBaseClient {

	public UserInvite(AuthNClient.Builder builder) {
		super(builder);
	}

	// TODO: Doc
	public UserInviteListResponse list(UserInviteListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/invite/list", request, UserInviteListResponse.class);
	}

	// TODO: Doc
	public UserInviteDeleteResponse delete(String id) throws PangeaException, PangeaAPIException {
		UserInviteDeleteRequest request = new UserInviteDeleteRequest(id);
		return post("/v1/user/invite/delete", request, UserInviteDeleteResponse.class);
	}
}
