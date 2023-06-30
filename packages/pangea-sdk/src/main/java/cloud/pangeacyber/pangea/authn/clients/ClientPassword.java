package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.responses.ClientPasswordChangeResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	public ClientPassword(AuthNClient.Builder builder) {
		super(builder);
	}

	// TODO: Doc
	public ClientPasswordChangeResponse change(String token, String oldPassword, String newPassword)
		throws PangeaException, PangeaAPIException {
		ClientPasswordChangeRequest request = new ClientPasswordChangeRequest(token, oldPassword, newPassword);
		ClientPasswordChangeResponse resp = post(
			"/v1/client/password/change",
			request,
			ClientPasswordChangeResponse.class
		);
		return resp;
	}
}
