package cloud.pangeacyber.pangea.store.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AuthenticatorType {
	EMAIL_OTP("email_otp"),
	PASSWORD("password"),
	SMS_OTP("sms_otp"),
	SOCIAL("social");

	private final String text;

	AuthenticatorType(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@JsonValue
	public String value() {
		return text;
	}
}
