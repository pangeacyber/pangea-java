package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.responses.ClientJWKSResponse;
import cloud.pangeacyber.pangea.authn.responses.ClientUserinfoResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

final class UserinfoRequest extends BaseRequest {

	@JsonProperty("code")
	String code;

	public UserinfoRequest(String code) {
		this.code = code;
	}
}

public class ClientEndpoint extends Client {

	public static final String serviceName = "authn";
	private ClientSession session;
	private ClientPassword password;
	private ClientToken token;

	public ClientEndpoint(Config config) {
		super(config, serviceName);
		session = new ClientSession(config);
		password = new ClientPassword(config);
		token = new ClientToken(config);
	}

	// TODO: Doc
	public ClientUserinfoResponse userinfo(String code) throws PangeaException, PangeaAPIException {
		UserinfoRequest request = new UserinfoRequest(code);
		ClientUserinfoResponse resp = doPost("/v1/client/userinfo", request, ClientUserinfoResponse.class);
		return resp;
	}

	public ClientJWKSResponse jwks() throws PangeaException, PangeaAPIException {
		return doPost("/v1/client/jwks", new BaseRequest(), ClientJWKSResponse.class);
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
