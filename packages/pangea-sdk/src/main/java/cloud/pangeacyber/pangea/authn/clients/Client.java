package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.responses.ClientJWKSResponse;
import cloud.pangeacyber.pangea.authn.responses.ClientUserinfoResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserinfoRequest extends BaseRequest {

	@JsonProperty("code")
	String code;

	public UserinfoRequest(String code) {
		this.code = code;
	}
}

public class Client extends AuthNBaseClient {

	private ClientSession session;
	private ClientPassword password;
	private ClientToken token;

	public Client(AuthNClient.Builder builder) {
		super(builder);
		session = new ClientSession(builder);
		password = new ClientPassword(builder);
		token = new ClientToken(builder);
	}

	/**
	 * Get User (client token)
	 * @pangea.description Retrieve the logged in user's token and information.
	 * @pangea.operationId authn_post_v1_client_userinfo
	 * @param code Login code returned by the login callback
	 * @return ClientUserinfoResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * ClientUserinfoResponse response = client.client().userinfo("pmc_d6chl6qulpn3it34oerwm3cqwsjd6dxw");
	 * }
	 */
	public ClientUserinfoResponse userinfo(String code) throws PangeaException, PangeaAPIException {
		UserinfoRequest request = new UserinfoRequest(code);
		ClientUserinfoResponse resp = post("/v2/client/userinfo", request, ClientUserinfoResponse.class);
		return resp;
	}

	/**
	 * Get JWT verification keys
	 * @pangea.description Get JWT verification keys.
	 * @pangea.operationId authn_post_v1_client_jwks
	 * @return ClientJWKSResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * ClientJWKSResponse response = client.client().jwks();
	 * }
	 */
	public ClientJWKSResponse jwks() throws PangeaException, PangeaAPIException {
		return post("/v2/client/jwks", new BaseRequest(), ClientJWKSResponse.class);
	}

	public ClientSession session() {
		return session;
	}

	public ClientPassword password() {
		return password;
	}

	public ClientToken token() {
		return token;
	}
}
