package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.*;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class ClientSessionInvalidateRequest extends BaseRequest {

	@JsonProperty("token")
	String token;

	@JsonProperty("session_id")
	String sessionID;

	public ClientSessionInvalidateRequest(String token, String sessionID) {
		this.token = token;
		this.sessionID = sessionID;
	}
}

final class ClientSessionRefreshRequest extends BaseRequest {

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

final class ClientSessionLogoutRequest extends BaseRequest {

	@JsonProperty("token")
	String token;

	public ClientSessionLogoutRequest(String token) {
		this.token = token;
	}
}

public class ClientSession extends AuthNBaseClient {

	public ClientSession(AuthNClient.Builder builder) {
		super(builder);
	}

	// TODO: Doc
	public ClientSessionInvalidateResponse invalidate(String token, String sessionID)
		throws PangeaException, PangeaAPIException {
		ClientSessionInvalidateRequest request = new ClientSessionInvalidateRequest(token, sessionID);
		return post("/v1/client/session/invalidate", request, ClientSessionInvalidateResponse.class);
	}

	// TODO: Doc
	public ClientSessionListResponse list(ClientSessionListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/client/session/list", request, ClientSessionListResponse.class);
	}

	// TODO: Doc
	public ClientSessionLogoutResponse logout(String token) throws PangeaException, PangeaAPIException {
		ClientSessionLogoutRequest request = new ClientSessionLogoutRequest(token);
		return post("/v1/client/session/logout", request, ClientSessionLogoutResponse.class);
	}

	// TODO: Doc
	public ClientSessionRefreshResponse refresh(String refreshToken) throws PangeaException, PangeaAPIException {
		ClientSessionRefreshRequest request = new ClientSessionRefreshRequest(refreshToken, null);
		return post("/v1/client/session/refresh", request, ClientSessionRefreshResponse.class);
	}

	// TODO: Doc
	public ClientSessionRefreshResponse refresh(String refreshToken, String userToken)
		throws PangeaException, PangeaAPIException {
		ClientSessionRefreshRequest request = new ClientSessionRefreshRequest(refreshToken, userToken);
		return post("/v1/client/session/refresh", request, ClientSessionRefreshResponse.class);
	}
}
