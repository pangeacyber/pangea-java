package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.responses.ClientUserinfoResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserinfoResquest {

	@JsonProperty("code")
	String code;

	public UserinfoResquest(String code) {
		this.code = code;
	}
}

public class ClientEndpoint extends Client {

	public static final String serviceName = "authn";
	private ClientSession session;

	public ClientEndpoint(Config config) {
		super(config, serviceName);
		session = new ClientSession(config);
	}

	// TODO: Doc
	public ClientUserinfoResponse userinfo(String code) throws PangeaException, PangeaAPIException {
		UserinfoResquest request = new UserinfoResquest(code);
		ClientUserinfoResponse resp = doPost("/v1/client/userinfo", request, ClientUserinfoResponse.class);
		return resp;
	}

	public ClientSession session() {
		return session;
	}
}
