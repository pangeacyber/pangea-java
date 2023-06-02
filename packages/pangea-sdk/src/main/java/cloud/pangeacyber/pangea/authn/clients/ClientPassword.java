package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
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

public class ClientPassword extends Client {

	public static final String serviceName = "authn";
	private static final boolean supportMultiConfig = false;

	public ClientPassword(Config config) {
		super(config, serviceName, supportMultiConfig);
	}

	// TODO: Doc
	public ClientPasswordChangeResponse change(String token, String oldPassword, String newPassword)
		throws PangeaException, PangeaAPIException {
		ClientPasswordChangeRequest request = new ClientPasswordChangeRequest(token, oldPassword, newPassword);
		ClientPasswordChangeResponse resp = doPost(
			"/v1/client/password/change",
			request,
			ClientPasswordChangeResponse.class
		);
		return resp;
	}
}
