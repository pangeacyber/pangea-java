package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import cloud.pangeacyber.pangea.authn.requests.FlowVerifyMFACompleteRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowEnrollMFACompleteResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowEnrollMFAStartResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class FlowEnrollMFAStartRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("mfa_provider")
	MFAProvider mfaProvider;

	public FlowEnrollMFAStartRequest(String flowID, MFAProvider mfaProvider) {
		this.flowID = flowID;
		this.mfaProvider = mfaProvider;
	}
}

public class FlowEnrollMFA extends Client {

	public static final String serviceName = "authn";

	public FlowEnrollMFA(Config config) {
		super(config, serviceName);
	}

	// TODO: Doc
	public FlowEnrollMFACompleteResponse complete(FlowVerifyMFACompleteRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/flow/enroll/mfa/complete", request, FlowEnrollMFACompleteResponse.class);
	}

	// TODO: Doc
	public FlowEnrollMFAStartResponse start(FlowEnrollMFAStartRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/flow/enroll/mfa/start", request, FlowEnrollMFAStartResponse.class);
	}
}
