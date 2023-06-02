package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.requests.FlowResetPasswordRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowResetPasswordResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class FlowReset extends Client {

	public static final String serviceName = "authn";
	private static final boolean supportMultiConfig = false;

	public FlowReset(Config config) {
		super(config, serviceName, supportMultiConfig);
	}

	public FlowResetPasswordResponse password(FlowResetPasswordRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/flow/reset/password", request, FlowResetPasswordResponse.class);
	}
}
