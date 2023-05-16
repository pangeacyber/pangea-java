package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.responses.ClientTokenCheckResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class TokenCheckResquest {

	@JsonProperty("token")
	String token;

	public TokenCheckResquest(String token) {
		this.token = token;
	}
}

public class ClientToken extends Client {

	public static final String serviceName = "authn";

	public ClientToken(Config config) {
		super(config, serviceName);
	}

	// TODO: Doc
	public ClientTokenCheckResponse check(String token) throws PangeaException, PangeaAPIException {
		TokenCheckResquest request = new TokenCheckResquest(token);
		ClientTokenCheckResponse resp = doPost("/v1/client/token/check", request, ClientTokenCheckResponse.class);
		return resp;
	}
}
