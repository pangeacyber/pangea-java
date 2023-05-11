package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.AuthNClient;
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

public class ClientToken extends BaseClient {



	public ClientToken(AuthNClient.Builder builder) {
		super(builder);
	}

	// TODO: Doc
	public ClientTokenCheckResponse check(String token) throws PangeaException, PangeaAPIException {
		TokenCheckResquest request = new TokenCheckResquest(token);
		ClientTokenCheckResponse resp = post("/v1/client/token/check", request, ClientTokenCheckResponse.class);
		return resp;
	}
}
