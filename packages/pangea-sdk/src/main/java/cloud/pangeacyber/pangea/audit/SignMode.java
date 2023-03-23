package cloud.pangeacyber.pangea.audit;

public enum SignMode {
	UNSIGNED("Unsigned"),
	LOCAL("Local");

	private final String text;

	SignMode(String text) {
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
