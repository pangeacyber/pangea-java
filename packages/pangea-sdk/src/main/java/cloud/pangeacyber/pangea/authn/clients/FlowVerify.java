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

	/**
	 * Verify Captcha
	 * @pangea.description Verify a CAPTCHA during a signup or signin flow.
	 * @pangea.operationId authn_post_v1_flow_verify_captcha
	 * @param flowId An ID for a login or signup flow
	 * @param code
	 * @return FlowVerifyCaptchaResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowVerifyCaptchaResponse response = client.flow().verify().captcha(
	 * 	"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 	"SOMEREALLYLONGANDOPAQUESTRINGFROMCAPTCHAVERIFICATION");
	 * }
	 */
	public FlowVerifyCaptchaResponse captcha(String flowId, String code) throws PangeaException, PangeaAPIException {
		FlowVerifyCaptchaRequest request = new FlowVerifyCaptchaRequest(flowId, code);
		return post("/v1/flow/verify/captcha", request, FlowVerifyCaptchaResponse.class);
	}

	/**
	 * Verify Email Address
	 * @pangea.description Verify an email address during a signup or signin flow.
	 * @pangea.operationId authn_post_v1_flow_verify_email
	 * @param request
	 * @return FlowVerifyEmailResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowVerifyEmailResponse response = client.flow().verify().email(
	 * 	new FlowVerifyEmailRequest()
	 * 		.Builder("pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh")
	 * 		.setCbState("pcb_zurr3lkcwdp5keq73htsfpcii5k4zgm7")
	 * 		.setCbCode("poc_fwg3ul4db1jpivexru3wyj354u9ej5e2").build());
	 * }
	 */
	public FlowVerifyEmailResponse email(FlowVerifyEmailRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/flow/verify/email", request, FlowVerifyEmailResponse.class);
	}

	/**
	 * Password Sign-in
	 * @pangea.description Sign in with a password.
	 * @pangea.operationId authn_post_v1_flow_verify_password
	 * @param request
	 * @return FlowVerifyPasswordResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowVerifyPasswordResponse response = client.flow().verify().password(
	 * 	new FlowVerifyPasswordRequest().Builder(
	 * 		"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 		"My1s+Password").build());
	 * }
	 */
	public FlowVerifyPasswordResponse password(FlowVerifyPasswordRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/flow/verify/password", request, FlowVerifyPasswordResponse.class);
	}

	/**
	 * Social Sign-in
	 * @pangea.description Signin with a social provider.
	 * @pangea.operationId authn_post_v1_flow_verify_social
	 * @param request
	 * @return FlowVerifySocialResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowVerifySocialResponse response = client.flow().verify().social(
	 * 	new FlowVerifySocialRequest()
	 * 		.Builder("pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh")
	 * 		.withCBState("pcb_zurr3lkcwdp5keq73htsfpcii5k4zgm7")
	 * 		.withCBCode("poc_fwg3ul4db1jpivexru3wyj354u9ej5e2").build());
	 * }
	 */
	public FlowVerifySocialResponse social(FlowVerifySocialRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/flow/verify/social", request, FlowVerifySocialResponse.class);
	}

	public FlowVerifyMFA mfa() {
		return mfa;
	}
}
