package cloud.pangeacyber.pangea.audit;

public enum EventVerification {
	NOT_VERIFIED("NotVerified"),
	SUCCESS("Success"),
	FAILED("Failed");

	private final String text;

	EventVerification(String text) {
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
