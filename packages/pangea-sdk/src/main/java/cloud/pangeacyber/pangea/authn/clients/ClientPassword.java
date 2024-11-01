package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.models.ExpirePasswordRequest;
import cloud.pangeacyber.pangea.authn.responses.ClientPasswordChangeResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

final class ClientPasswordChangeRequest extends BaseRequest {

	@JsonProperty("token")
	String token;

	@JsonProperty("old_password")
	String oldSecret;

	@JsonProperty("new_password")
	String newSecret;

	public ClientPasswordChangeRequest(String token, String oldSecret, String newSecret) {
		this.token = token;
		this.oldSecret = oldSecret;
		this.newSecret = newSecret;
	}
}

public class ClientPassword extends AuthNBaseClient {

	/** @hidden */
	public ClientPassword(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Change a user's password
	 * @pangea.description Change a user's password given the current password.
	 * @pangea.operationId authn_post_v2_client_password_change
	 * @param token A user token value
	 * @param oldPassword
	 * @param newPassword
	 * @return ClientPasswordChangeResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * ClientPasswordChangeResponse response = client.client().password().change(
	 * 	"ptu_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a",
	 * 	"hunter2",
	 * 	"My2n+Password");
	 * }
	 */
	public ClientPasswordChangeResponse change(String token, String oldPassword, String newPassword)
		throws PangeaException, PangeaAPIException {
		ClientPasswordChangeRequest request = new ClientPasswordChangeRequest(token, oldPassword, newPassword);
		ClientPasswordChangeResponse resp = post(
			"/v2/client/password/change",
			request,
			ClientPasswordChangeResponse.class
		);
		return resp;
	}

	/**
	 * Expire a user's password
	 * @pangea.description Expire a user's password.
	 * @pangea.operationId authn_post_v2_user_password_expire
	 * @param id The identity of a user or a service.
	 * @return An empty object.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * client.client().password().expire("pui_[...]");
	 * }
	 */
	public Response<Void> expire(String id) throws PangeaException, PangeaAPIException {
		return post("/v2/user/password/expire", new ExpirePasswordRequest(id), new TypeReference<Response<Void>>() {});
	}
}
