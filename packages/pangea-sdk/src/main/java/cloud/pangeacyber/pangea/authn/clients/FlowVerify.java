package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.FlowVerifyEmailRequest;
import cloud.pangeacyber.pangea.authn.requests.FlowVerifyPasswordRequest;
import cloud.pangeacyber.pangea.authn.requests.FlowVerifySocialRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyCaptchaResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyEmailResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyPasswordResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifySocialResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class FlowVerifyCaptchaRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("code")
	String code;

	public FlowVerifyCaptchaRequest(String flowID, String code) {
		this.flowID = flowID;
		this.code = code;
	}
}

public class FlowVerify extends AuthNBaseClient {

	private FlowVerifyMFA mfa;

	public FlowVerify(AuthNClient.Builder builder) {
		super(builder);
		mfa = new FlowVerifyMFA(builder);
	}

	// TODO: doc
	public FlowVerifyCaptchaResponse captcha(String flowId, String code) throws PangeaException, PangeaAPIException {
		FlowVerifyCaptchaRequest request = new FlowVerifyCaptchaRequest(flowId, code);
		return post("/v1/flow/verify/captcha", request, FlowVerifyCaptchaResponse.class);
	}

	// TODO: doc
	public FlowVerifyEmailResponse email(FlowVerifyEmailRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/flow/verify/email", request, FlowVerifyEmailResponse.class);
	}

	// TODO: doc
	public FlowVerifyPasswordResponse password(FlowVerifyPasswordRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/verify/password", request, FlowVerifyPasswordResponse.class);
	}

	// TODO: doc
	public FlowVerifySocialResponse social(FlowVerifySocialRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/flow/verify/social", request, FlowVerifySocialResponse.class);
	}

	public FlowVerifyMFA mfa() {
		return mfa;
	}
}
