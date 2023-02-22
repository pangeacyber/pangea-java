package cloud.pangeacyber.pangea;

public enum TestEnvironment {
	LIVE("LVE"),
	DEVELOP("DEV"),
	STAGING("STG");

	private final String text;

	TestEnvironment(String text) {
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
