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

	/**
	 * Check a token
	 * @pangea.description Look up a token and return its contents.
	 * @pangea.operationId authn_post_v1_client_token_check
	 * @param token A token value
	 * @return ClientTokenCheckResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * ClientTokenCheckResponse response = client.client().token().check("ptu_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a");
	 * }
	 */
	public ClientTokenCheckResponse check(String token) throws PangeaException, PangeaAPIException {
		TokenCheckRequest request = new TokenCheckRequest(token);
		ClientTokenCheckResponse resp = post("/v2/client/token/check", request, ClientTokenCheckResponse.class);
		return resp;
	}
}
