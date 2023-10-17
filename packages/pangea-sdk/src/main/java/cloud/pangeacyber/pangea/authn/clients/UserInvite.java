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

	/**
	 * List Invites
	 * @pangea.description Look up active invites for the userpool.
	 * @pangea.operationId authn_post_v1_user_invite_list
	 * @param request
	 * @return UserInviteListResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserInviteListResponse response = client.user().invite().list(
	 * 	new UserInviteListRequest.Builder().build());
	 * }
	 */
	public UserInviteListResponse list(UserInviteListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/user/invite/list", request, UserInviteListResponse.class);
	}

	/**
	 * Delete Invite
	 * @pangea.description Delete a user invitation.
	 * @pangea.operationId authn_post_v1_user_invite_delete
	 * @param id
	 * @return UserInviteDeleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.user().invite().delete("pmc_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a");
	 * }
	 */
	public UserInviteDeleteResponse delete(String id) throws PangeaException, PangeaAPIException {
		UserInviteDeleteRequest request = new UserInviteDeleteRequest(id);
		return post("/v2/user/invite/delete", request, UserInviteDeleteResponse.class);
	}
}
