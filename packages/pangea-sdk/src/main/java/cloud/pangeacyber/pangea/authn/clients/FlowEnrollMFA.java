package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.FlowEnrollMFAStartRequest;
import cloud.pangeacyber.pangea.authn.requests.FlowVerifyMFACompleteRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowEnrollMFACompleteResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowEnrollMFAStartResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class FlowEnrollMFA extends AuthNBaseClient {

	public FlowEnrollMFA(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Complete MFA Enrollment
	 * @pangea.description Complete MFA enrollment by verifying a trial MFA code.
	 * @pangea.operationId authn_post_v1_flow_enroll_mfa_complete
	 * @param request
	 * @return FlowEnrollMFACompleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowEnrollMFACompleteResponse response = client.flow().enroll().mfa().complete(
	 * 	new FlowVerifyMFACompleteRequest.Builder(
	 * 		"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 		"391423").build());
	 * }
	 */
	public FlowEnrollMFACompleteResponse complete(FlowVerifyMFACompleteRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/enroll/mfa/complete", request, FlowEnrollMFACompleteResponse.class);
	}

	/**
	 * Start MFA Enrollment
	 * @pangea.description Start the process of enrolling an MFA.
	 * @pangea.operationId authn_post_v1_flow_enroll_mfa_start
	 * @param request
	 * @return FlowEnrollMFAStartResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowEnrollMFAStartResponse response = client.flow().enroll().mfa().start(
	 * 	new FlowEnrollMFAStartRequest().Builder(
	 * 		"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh", 
	 * 		MFAProvider.SMS_OTP).setPhone("1-808-555-0173").build());
	 * }
	 */
	public FlowEnrollMFAStartResponse start(FlowEnrollMFAStartRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/enroll/mfa/start", request, FlowEnrollMFAStartResponse.class);
	}
}
