package cloud.pangeacyber.pangea;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransferMethod {
	DIRECT("direct"),
	MULTIPART("multipart"),
	POST_URL("post-url"),
	PUT_URL("put-url");

	private final String text;

	TransferMethod(String text) {
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
