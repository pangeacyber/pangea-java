package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.responses.ClientPasswordChangeResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class ClientPasswordChangeResquest {

	@JsonProperty("token")
	String token;

	@JsonProperty("old_password")
	String oldSecret;

	@JsonProperty("new_password")
	String newSecret;

	public ClientPasswordChangeResquest(String token, String oldSecret, String newSecret) {
		this.token = token;
		this.oldSecret = oldSecret;
		this.newSecret = newSecret;
	}
}

public class ClientPassword extends BaseClient {



	public ClientPassword(AuthNClient.Builder builder) {
		super(builder);
	}

	// TODO: Doc
	public ClientPasswordChangeResponse change(String token, String oldPassword, String newPassword)
		throws PangeaException, PangeaAPIException {
		ClientPasswordChangeResquest request = new ClientPasswordChangeResquest(token, oldPassword, newPassword);
		ClientPasswordChangeResponse resp = post(
			"/v1/client/password/change",
			request,
			ClientPasswordChangeResponse.class
		);
		return resp;
	}
}
