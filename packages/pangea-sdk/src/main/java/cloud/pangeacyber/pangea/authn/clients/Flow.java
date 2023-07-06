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

	// TODO: Doc
	public FlowCompleteResponse complete(String flowID) throws PangeaException, PangeaAPIException {
		FlowCompleteRequest request = new FlowCompleteRequest(flowID);
		return post("/v1/flow/complete", request, FlowCompleteResponse.class);
	}

	// TODO: Doc
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
