package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.responses.ClientJWKSResponse;
import cloud.pangeacyber.pangea.authn.responses.ClientUserinfoResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

final class UserinfoResquest {

	@JsonProperty("code")
	String code;

	public UserinfoResquest(String code) {
		this.code = code;
	}
}

public class Client extends BaseClient {

	private ClientSession session;
	private ClientPassword password;
	private ClientToken token;

	public Client(AuthNClient.Builder builder) {
		super(builder);
		session = new ClientSession(builder);
		password = new ClientPassword(builder);
		token = new ClientToken(builder);
	}

	// TODO: Doc
	public ClientUserinfoResponse userinfo(String code) throws PangeaException, PangeaAPIException {
		UserinfoResquest request = new UserinfoResquest(code);
		ClientUserinfoResponse resp = post("/v1/client/userinfo", request, ClientUserinfoResponse.class);
		return resp;
	}

	public ClientJWKSResponse jwks() throws PangeaException, PangeaAPIException {
		return post("/v1/client/jwks", new HashMap<String, String>(), ClientJWKSResponse.class);
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
