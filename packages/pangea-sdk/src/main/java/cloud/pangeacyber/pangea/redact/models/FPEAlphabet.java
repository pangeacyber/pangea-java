package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FPEAlphabet {
	NUMERIC("numeric"),
	ALPHANUMERICLOWER("alphanumericlower"),
	ALPHANUMERIC("alphanumeric");

	private final String text;

	FPEAlphabet(String text) {
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
