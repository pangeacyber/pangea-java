package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.EnrollMFAComplete;
import cloud.pangeacyber.pangea.authn.models.EnrollMFAStart;
import cloud.pangeacyber.pangea.authn.models.Signup;
import cloud.pangeacyber.pangea.authn.models.VerifyCaptcha;
import cloud.pangeacyber.pangea.authn.models.VerifyMFAStart;
import cloud.pangeacyber.pangea.authn.models.VerifyPassword;
import cloud.pangeacyber.pangea.authn.models.VerifySocial;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonFlowResult {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("next_step")
	String nextStep;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("error")
	String error;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("complete")
	Map<String, Object> complete;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("enroll_mfa_start")
	EnrollMFAStart enrollMFAStart;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("enroll_mfa_complete")
	EnrollMFAComplete enrollMFAComplete;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("signup")
	Signup signup;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verify_captcha")
	VerifyCaptcha verifyCaptcha;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verify_email")
	Map<String, Object> verifyEmail;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verify_mfa_start")
	VerifyMFAStart verifyMFAStart;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verify_mfa_complete")
	Map<String, Object> verifyMFAComplete;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verify_password")
	VerifyPassword verifyPassword;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verify_social")
	VerifySocial verifySocial;

	public String getFlowID() {
		return flowID;
	}

	public String getNextStep() {
		return nextStep;
	}

	public String getError() {
		return error;
	}

	public Map<String, Object> getComplete() {
		return complete;
	}

	public EnrollMFAStart getEnrollMFAStart() {
		return enrollMFAStart;
	}

	public EnrollMFAComplete getEnrollMFAComplete() {
		return enrollMFAComplete;
	}

	public Signup getSignup() {
		return signup;
	}

	public VerifyCaptcha getVerifyCaptcha() {
		return verifyCaptcha;
	}

	public Map<String, Object> getVerifyEmail() {
		return verifyEmail;
	}

	public VerifyMFAStart getVerifyMFAStart() {
		return verifyMFAStart;
	}

	public Map<String, Object> getVerifyMFAComplete() {
		return verifyMFAComplete;
	}

	public VerifyPassword getVerifyPassword() {
		return verifyPassword;
	}

	public VerifySocial getVerifySocial() {
		return verifySocial;
	}
}
