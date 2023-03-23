package cloud.pangeacyber.pangea.authn.models;

public enum MFAProvider {
	TOTP("totp"),
	EMAIL_OTP("email_otp"),
	SMS_OTP("sms_otp");

	private final String text;

	MFAProvider(String text) {
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
