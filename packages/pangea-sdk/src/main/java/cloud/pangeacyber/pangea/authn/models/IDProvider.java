package cloud.pangeacyber.pangea.authn.models;

public enum IDProvider {
	FACEBOOK("facebook"),
	GITHUB("github"),
	GOOGLE("google"),
	MICROSOFT("microsoftonline"),
	PASSWORD("password");

	private final String text;

	IDProvider(String text) {
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
