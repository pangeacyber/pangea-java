package cloud.pangeacyber.pangea.authn.models;

public enum FlowType {
	SIGNIN("signin"),
	SIGNUP("signup");

	private final String text;

	FlowType(String text) {
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
