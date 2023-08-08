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

	/**
	 * Invalidate Session | Client
	 * @pangea.description Invalidate a session by session ID using a client token.
	 * @pangea.operationId authn_post_v1_client_session_invalidate
	 * @param token A user token value
	 * @param sessionID An ID for a token
	 * @return ClientSessionInvalidateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.client().session().invalidate(
	 * 	"ptu_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a",
	 * 	"pmt_zppkzrjguxyblaia6itbiesejn7jejnr");
	 * }
	 */
	public ClientSessionInvalidateResponse invalidate(String token, String sessionID)
		throws PangeaException, PangeaAPIException {
		ClientSessionInvalidateRequest request = new ClientSessionInvalidateRequest(token, sessionID);
		return post("/v1/client/session/invalidate", request, ClientSessionInvalidateResponse.class);
	}

	/**
	 * List sessions (client token)
	 * @pangea.description List sessions using a client token.
	 * @pangea.operationId authn_post_v1_client_session_list
	 * @param request
	 * @return ClientSessionListResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * ClientSessionListResponse response = client.client().session().list(
	 * 	new ClientSessionListRequest.Builder("ptu_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a").build());
	 * }
	 */
	public ClientSessionListResponse list(ClientSessionListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/client/session/list", request, ClientSessionListResponse.class);
	}

	/**
	 * Log out (client token)
	 * @pangea.description Log out the current user's session.
	 * @pangea.operationId authn_post_v1_client_session_logout
	 * @param token A user token value
	 * @return ClientSessionLogoutResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.client().session().logout("ptu_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a");
	 * }
	 */
	public ClientSessionLogoutResponse logout(String token) throws PangeaException, PangeaAPIException {
		ClientSessionLogoutRequest request = new ClientSessionLogoutRequest(token);
		return post("/v1/client/session/logout", request, ClientSessionLogoutResponse.class);
	}

	/**
	 * Refresh a Session
	 * @pangea.description Refresh a session token.
	 * @param refreshToken A refresh token value
	 * @return ClientSessionRefreshResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * ClientSessionRefreshResponse response = client.client().session().refresh(
	 * 	"ptr_xpkhwpnz2cmegsws737xbsqnmnuwtbm5");
	 * }
	 */
	public ClientSessionRefreshResponse refresh(String refreshToken) throws PangeaException, PangeaAPIException {
		ClientSessionRefreshRequest request = new ClientSessionRefreshRequest(refreshToken, null);
		return post("/v1/client/session/refresh", request, ClientSessionRefreshResponse.class);
	}

	/**
	 * Refresh a Session
	 * @pangea.description Refresh a session token.
	 * @pangea.operationId authn_post_v1_client_session_refresh
	 * @param refreshToken
	 * @param userToken
	 * @return ClientSessionRefreshResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * ClientSessionRefreshResponse response = client.client().session().refresh(
	 * 	"ptr_xpkhwpnz2cmegsws737xbsqnmnuwtbm5", "ptu_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a");
	 * }
	 */
	public ClientSessionRefreshResponse refresh(String refreshToken, String userToken)
		throws PangeaException, PangeaAPIException {
		ClientSessionRefreshRequest request = new ClientSessionRefreshRequest(refreshToken, userToken);
		return post("/v1/client/session/refresh", request, ClientSessionRefreshResponse.class);
	}
}
