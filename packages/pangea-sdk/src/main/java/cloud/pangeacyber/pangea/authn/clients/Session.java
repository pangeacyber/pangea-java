package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.*;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class SessionInvalidateRequest extends BaseRequest {

	@JsonProperty("session_id")
	String sessionID;

	public SessionInvalidateRequest(String sessionID) {
		this.sessionID = sessionID;
	}
}

final class SessionLogoutRequest extends BaseRequest {

	@JsonProperty("user_id")
	String userID;

	public SessionLogoutRequest(String userID) {
		this.userID = userID;
	}
}

public class Session extends AuthNBaseClient {

	public Session(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Invalidate Session
	 * @pangea.description Invalidate a session by session ID.
	 * @pangea.operationId authn_post_v1_session_invalidate
	 * @param sessionID
	 * @return SessionInvalidateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.session().invalidate("pmt_zppkzrjguxyblaia6itbiesejn7jejnr");
	 * }
	 */
	public SessionInvalidateResponse invalidate(String sessionID) throws PangeaException, PangeaAPIException {
		SessionInvalidateRequest request = new SessionInvalidateRequest(sessionID);
		return post("/v1/session/invalidate", request, SessionInvalidateResponse.class);
	}

	/**
	 * List session (service token)
	 * @pangea.description List sessions.
	 * @pangea.operationId authn_post_v1_session_list
	 * @param request
	 * @return SessionListResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SessionListResponse response = client.session().list(
	 * 	new SessionListRequest().Builder().build());
	 * }
	 */
	public SessionListResponse list(SessionListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/session/list", request, SessionListResponse.class);
	}

	/**
	 * Log out (service token)
	 * @pangea.description Invalidate all sessions belonging to a user.
	 * @pangea.operationId authn_post_v1_session_logout
	 * @param userID The identity of a user or a service
	 * @return SessionLogoutResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.session().logout("pui_xpkhwpnz2cmegsws737xbsqnmnuwtvm5");
	 * }
	 */
	public SessionLogoutResponse logout(String userID) throws PangeaException, PangeaAPIException {
		SessionLogoutRequest request = new SessionLogoutRequest(userID);
		return post("/v1/session/logout", request, SessionLogoutResponse.class);
	}
}
