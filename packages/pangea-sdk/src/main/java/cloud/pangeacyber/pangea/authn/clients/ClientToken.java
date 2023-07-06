package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
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

public class ClientToken extends AuthNBaseClient {

	public ClientToken(AuthNClient.Builder builder) {
		super(builder);
	}

	// TODO: Doc
	public ClientTokenCheckResponse check(String token) throws PangeaException, PangeaAPIException {
		TokenCheckRequest request = new TokenCheckRequest(token);
		ClientTokenCheckResponse resp = post("/v1/client/token/check", request, ClientTokenCheckResponse.class);
		return resp;
	}
}
