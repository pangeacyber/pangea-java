package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.FlowResetPasswordRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowResetPasswordResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class FlowReset extends AuthNBaseClient {

	public FlowReset(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Password Reset
	 * @pangea.description Reset password during sign-in.
	 * @pangea.operationId authn_post_v1_flow_reset_password
	 * @param request
	 * @return FlowResetPasswordResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowResetPasswordResponse response = client.flow().reset().password(
	 * 	new FlowResetPasswordRequest.Builder(
	 * 		"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 		"My1s+Password").build());
	 * }
	 */
	public FlowResetPasswordResponse password(FlowResetPasswordRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/reset/password", request, FlowResetPasswordResponse.class);
	}
}
