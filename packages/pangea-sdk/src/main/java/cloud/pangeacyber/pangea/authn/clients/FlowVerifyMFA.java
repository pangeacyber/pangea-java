package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.AuthNClient;
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

public class FlowVerifyMFA extends BaseClient {



	public FlowVerifyMFA(AuthNClient.Builder builder) {
		super(builder);
	}

	// TODO: Doc
	public FlowVerifyMFACompleteResponse complete(FlowVerifyMFACompleteRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/verify/mfa/complete", request, FlowVerifyMFACompleteResponse.class);
	}

	// TODO: Doc
	public FlowVerifyMFAStartResponse start(String flowID, MFAProvider mfaProvider)
		throws PangeaException, PangeaAPIException {
		FlowVerifyMFAStartRequest request = new FlowVerifyMFAStartRequest(flowID, mfaProvider);
		return post("/v1/flow/verify/mfa/start", request, FlowVerifyMFAStartResponse.class);
	}
}
