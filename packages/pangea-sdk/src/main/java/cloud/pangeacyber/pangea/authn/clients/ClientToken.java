package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.responses.ClientTokenCheckResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class TokenCheckRequest extends BaseRequest {

	@JsonProperty("token")
	String token;

	public TokenCheckRequest(String token) {
		this.token = token;
	}
}

public class ClientToken extends Client {

	public static final String serviceName = "authn";
	private static final boolean supportMultiConfig = false;

	public ClientToken(Config config) {
		super(config, serviceName, supportMultiConfig);
	}

	// TODO: Doc
	public ClientTokenCheckResponse check(String token) throws PangeaException, PangeaAPIException {
		TokenCheckRequest request = new TokenCheckRequest(token);
		ClientTokenCheckResponse resp = doPost("/v1/client/token/check", request, ClientTokenCheckResponse.class);
		return resp;
	}
}
