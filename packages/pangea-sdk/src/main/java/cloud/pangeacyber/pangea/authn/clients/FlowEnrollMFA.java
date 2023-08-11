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

	// TODO: Doc
	public FlowEnrollMFACompleteResponse complete(FlowVerifyMFACompleteRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/enroll/mfa/complete", request, FlowEnrollMFACompleteResponse.class);
	}

	// TODO: Doc
	public FlowEnrollMFAStartResponse start(FlowEnrollMFAStartRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/enroll/mfa/start", request, FlowEnrollMFAStartResponse.class);
	}
}
