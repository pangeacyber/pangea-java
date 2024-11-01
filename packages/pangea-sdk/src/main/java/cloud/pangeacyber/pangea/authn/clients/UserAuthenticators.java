package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.UserAuthenticatorsDeleteRequest;
import cloud.pangeacyber.pangea.authn.requests.UserAuthenticatorsListRequest;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class UserAuthenticators extends AuthNBaseClient {

	/** @hidden */
	public UserAuthenticators(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Delete user authenticator
	 * @pangea.description Delete user authenticator.
	 * @pangea.operationId authn_post_v2_user_authenticators_delete
	 * @param request Request parameters.
	 * @return An empty object.
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserAuthenticatorsDeleteRequest request =
	 * 	new UserAuthenticatorsDeleteRequest
	 * 		.Builder("pau_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a")
	 * 		.setID("pui_xpkhwpnz2cmegsws737xbsqnmnuwtbm5")
	 * 		.build();
	 *
	 * client.user().authenticators().delete(request);
	 * }
	 */
	public UserAuthenticatorsDeleteResponse delete(UserAuthenticatorsDeleteRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/user/authenticators/delete", request, UserAuthenticatorsDeleteResponse.class);
	}

	/**
	 * Get user authenticators
	 * @pangea.description Get user authenticators.
	 * @pangea.operationId authn_post_v2_user_authenticators_list
	 * @param request Request parameters.
	 * @return User's authenticators.
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserAuthenticatorsListRequest request =
	 * 	new UserAuthenticatorsListRequest
	 * 		.Builder()
	 * 		.setID("pui_xpkhwpnz2cmegsws737xbsqnmnuwtbm5")
	 * 		.build();
	 *
	 * UserAuthenticatorsListResponse response =
	 * 	client.user().authenticators.list(request);
	 * }
	 */
	public UserAuthenticatorsListResponse list(UserAuthenticatorsListRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/user/authenticators/list", request, UserAuthenticatorsListResponse.class);
	}
}
