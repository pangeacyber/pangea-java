package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.FlowResetPasswordRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowResetPasswordResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class FlowReset extends BaseClient {



	public FlowReset(AuthNClient.Builder builder) {
		super(builder);
	}

	public FlowResetPasswordResponse password(FlowResetPasswordRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/reset/password", request, FlowResetPasswordResponse.class);
	}
}
