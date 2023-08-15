package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import cloud.pangeacyber.pangea.authn.requests.FlowVerifyMFACompleteRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyMFACompleteResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyMFAStartResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class FlowVerifyMFAStartRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("mfa_provider")
	MFAProvider mfaProvider;

	public FlowVerifyMFAStartRequest(String flowID, MFAProvider mfaProvider) {
		this.flowID = flowID;
		this.mfaProvider = mfaProvider;
	}
}

public class FlowVerifyMFA extends AuthNBaseClient {

	public FlowVerifyMFA(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Complete MFA Verification
	 * @pangea.description Complete MFA verification.
	 * @pangea.operationId authn_post_v1_flow_verify_mfa_complete
	 * @param request
	 * @return FlowVerifyMFACompleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowVerifyMFACompleteResponse response = client.flow().verify().mfa().complete(
	 * 	new FlowVerifyMFACompleteRequest().Builder(
	 * 		"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 		"999999").build());
	 * }
	 */
	public FlowVerifyMFACompleteResponse complete(FlowVerifyMFACompleteRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/verify/mfa/complete", request, FlowVerifyMFACompleteResponse.class);
	}

	/**
	 * Start MFA Verification
	 * @pangea.description Start the process of MFA verification.
	 * @pangea.operationId authn_post_v1_flow_verify_mfa_start
	 * @param flowID
	 * @param mfaProvider
	 * @return FlowVerifyMFAStartResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowVerifyMFAStartResponse response = client.flow().verify().mfa().start(
	 * 	"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 	MFAProvider.TOTP);
	 * }
	 */
	public FlowVerifyMFAStartResponse start(String flowID, MFAProvider mfaProvider)
		throws PangeaException, PangeaAPIException {
		FlowVerifyMFAStartRequest request = new FlowVerifyMFAStartRequest(flowID, mfaProvider);
		return post("/v1/flow/verify/mfa/start", request, FlowVerifyMFAStartResponse.class);
	}
}
