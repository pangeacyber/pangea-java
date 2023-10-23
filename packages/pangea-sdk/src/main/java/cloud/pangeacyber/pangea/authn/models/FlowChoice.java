package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FlowChoice {
	AGREEMENTS("agreements"),
	CAPTCHA("captcha"),
	EMAIL_OTP("email_otp"),
	MAGICLINK("magiclink"),
	PASSWORD("password"),
	PROFILE("profile"),
	PROVISIONAL_ENROLLMENT("provisional_enrollment"),
	RESET_PASSWORD("reset_password"),
	SET_EMAIL("set_mail"),
	SET_PASSWORD("set_password"),
	SMS_OTP("sms_otp"),
	SOCIAL("social"),
	TOTP("totp"),
	VERIFY_EMAIL("verify_email");

	private final String text;

	FlowChoice(String text) {
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}

	@JsonValue
	final String value() {
		return text;
	}
}
