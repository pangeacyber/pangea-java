package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.UserAuthenticatorsDeleteRequest;
import cloud.pangeacyber.pangea.authn.requests.UserAuthenticatorsListRequest;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class UserAuthenticators extends AuthNBaseClient {

	public UserAuthenticators(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	// TODO: Docs
	 */
	public UserAuthenticatorsDeleteResponse delete(UserAuthenticatorsDeleteRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/user/authenticators/delete", request, UserAuthenticatorsDeleteResponse.class);
	}

	/**
	// TODO: Docs
	 */
	public UserAuthenticatorsListResponse list(UserAuthenticatorsListRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/user/authenticators/list", request, UserAuthenticatorsListResponse.class);
	}
}
