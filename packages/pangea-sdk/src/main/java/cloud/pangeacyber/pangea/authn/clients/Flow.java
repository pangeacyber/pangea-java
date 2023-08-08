package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.FlowStartRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowCompleteResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowStartResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class FlowCompleteRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flowID;

	public FlowCompleteRequest(String flowID) {
		this.flowID = flowID;
	}
}

public class Flow extends AuthNBaseClient {

	private FlowEnroll enroll;
	private FlowSignup signup;
	private FlowVerify verify;
	private FlowReset reset;

	public Flow(AuthNClient.Builder builder) {
		super(builder);
		enroll = new FlowEnroll(builder);
		signup = new FlowSignup(builder);
		verify = new FlowVerify(builder);
		reset = new FlowReset(builder);
	}

	public FlowEnroll getEnroll() {
		return enroll;
	}

	public FlowSignup getSignup() {
		return signup;
	}

	public FlowVerify getVerify() {
		return verify;
	}

	public FlowReset getReset() {
		return reset;
	}

	/**
	 * Complete Sign-up/in
	 * @pangea.description Complete a login or signup flow.
	 * @pangea.operationId authn_post_v1_flow_complete
	 * @param flowID An ID for a login or signup flow
	 * @return FlowCompleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowCompleteResponse response = client.flow().complete("pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh");
	 * }
	 */
	public FlowCompleteResponse complete(String flowID) throws PangeaException, PangeaAPIException {
		FlowCompleteRequest request = new FlowCompleteRequest(flowID);
		return post("/v1/flow/complete", request, FlowCompleteResponse.class);
	}

	/**
	 * Start a sign-up/in
	 * @pangea.description Start a new signup or signin flow.
	 * @pangea.operationId authn_post_v1_flow_start
	 * @param request
	 * @return FlowStartResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowStartResponse response = client.flow().start(
	 * 	new FlowStartRequest.Builder()
	 * 		.setCbURI("https://www.myserver.com/callback")
	 * 		.setEmail("joe.user@email.com")
	 * 		.setFlowType(FlowType.SIGNIN)
	 * 		.setProvider(IDProvider.PASSWORD)
	 * 		.build());
	 * }
	 */
	public FlowStartResponse start(FlowStartRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/flow/start", request, FlowStartResponse.class);
	}

	public FlowEnroll enroll() {
		return enroll;
	}

	public FlowSignup signup() {
		return signup;
	}

	public FlowVerify verify() {
		return verify;
	}
}
