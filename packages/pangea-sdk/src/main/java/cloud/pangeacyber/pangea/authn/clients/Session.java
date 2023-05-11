package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.*;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class SessionInvalidateRequest {

	@JsonProperty("session_id")
	String sessionID;

	public SessionInvalidateRequest(String sessionID) {
		this.sessionID = sessionID;
	}
}

final class SessionRefreshRequest {

	@JsonProperty("refresh_token")
	String refreshToken;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("user_token")
	String userToken;

	public SessionRefreshRequest(String refreshToken, String userToken) {
		this.refreshToken = refreshToken;
		this.userToken = userToken;
	}
}

final class SessionLogoutRequest {

	@JsonProperty("user_id")
	String userID;

	public SessionLogoutRequest(String userID) {
		this.userID = userID;
	}
}

public class Session extends BaseClient {



	public Session(AuthNClient.Builder builder) {
		super(builder);
	}

	// TODO: Doc
	public SessionInvalidateResponse invalidate(String sessionID) throws PangeaException, PangeaAPIException {
		SessionInvalidateRequest request = new SessionInvalidateRequest(sessionID);
		return post("/v1/session/invalidate", request, SessionInvalidateResponse.class);
	}

	// TODO: Doc
	public SessionListResponse list(SessionListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/session/list", request, SessionListResponse.class);
	}

	// TODO: Doc
	public SessionLogoutResponse logout(String userID) throws PangeaException, PangeaAPIException {
		SessionLogoutRequest request = new SessionLogoutRequest(userID);
		return post("/v1/session/logout", request, SessionLogoutResponse.class);
	}
}
