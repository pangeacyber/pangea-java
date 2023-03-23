package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import cloud.pangeacyber.pangea.authn.requests.FlowVerifyMFACompleteRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyMFACompleteResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyMFAStartResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class FlowVerifyMFAStartRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("mfa_provider")
	MFAProvider mfaProvider;

	public FlowVerifyMFAStartRequest(String flowID, MFAProvider mfaProvider) {
		this.flowID = flowID;
		this.mfaProvider = mfaProvider;
	}
}

public class FlowVerifyMFA extends Client {

	public static final String serviceName = "authn";

	public FlowVerifyMFA(Config config) {
		super(config, serviceName);
	}

	// TODO: Doc
	public FlowVerifyMFACompleteResponse complete(FlowVerifyMFACompleteRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/flow/verify/mfa/complete", request, FlowVerifyMFACompleteResponse.class);
	}

	// TODO: Doc
	public FlowVerifyMFAStartResponse start(String flowID, MFAProvider mfaProvider)
		throws PangeaException, PangeaAPIException {
		FlowVerifyMFAStartRequest request = new FlowVerifyMFAStartRequest(flowID, mfaProvider);
		return doPost("/v1/flow/verify/mfa/start", request, FlowVerifyMFAStartResponse.class);
	}
}
