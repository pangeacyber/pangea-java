package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MaskingType {
	MASK("mask"),
	UNMASK("unmask");

	private final String text;

	MaskingType(String text) {
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
