package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.requests.*;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class ClientSessionInvalidateRequest {

	@JsonProperty("token")
	String token;

	@JsonProperty("session_id")
	String sessionID;

	public ClientSessionInvalidateRequest(String token, String sessionID) {
		this.token = token;
		this.sessionID = sessionID;
	}
}

final class ClientSessionRefreshRequest {

	@JsonProperty("refresh_token")
	String refreshToken;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("user_token")
	String userToken;

	public ClientSessionRefreshRequest(String refreshToken, String userToken) {
		this.refreshToken = refreshToken;
		this.userToken = userToken;
	}
}

final class ClientSessionLogoutRequest {

	@JsonProperty("token")
	String token;

	public ClientSessionLogoutRequest(String token) {
		this.token = token;
	}
}

public class ClientSession extends Client {

	public static final String serviceName = "authn";

	public ClientSession(Config config) {
		super(config, serviceName);
	}

	// TODO: Doc
	public ClientSessionInvalidateResponse invalidate(String token, String sessionID)
		throws PangeaException, PangeaAPIException {
		ClientSessionInvalidateRequest request = new ClientSessionInvalidateRequest(token, sessionID);
		return doPost("/v1/client/session/invalidate", request, ClientSessionInvalidateResponse.class);
	}

	// TODO: Doc
	public ClientSessionListResponse list(ClientSessionListRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/client/session/list", request, ClientSessionListResponse.class);
	}

	// TODO: Doc
	public ClientSessionLogoutResponse logout(String token) throws PangeaException, PangeaAPIException {
		ClientSessionLogoutRequest request = new ClientSessionLogoutRequest(token);
		return doPost("/v1/client/session/logout", request, ClientSessionLogoutResponse.class);
	}

	// TODO: Doc
	public ClientSessionRefreshResponse refresh(String refreshToken) throws PangeaException, PangeaAPIException {
		ClientSessionRefreshRequest request = new ClientSessionRefreshRequest(refreshToken, null);
		return doPost("/v1/client/session/refresh", request, ClientSessionRefreshResponse.class);
	}

	// TODO: Doc
	public ClientSessionRefreshResponse refresh(String refreshToken, String userToken)
		throws PangeaException, PangeaAPIException {
		ClientSessionRefreshRequest request = new ClientSessionRefreshRequest(refreshToken, userToken);
		return doPost("/v1/client/session/refresh", request, ClientSessionRefreshResponse.class);
	}
}
