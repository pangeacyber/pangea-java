package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RedactType {
	MASK("mask"),
	PARTIAL_MASKING("partial_masking"),
	REPLACEMENT("replacement"),
	DETECT_ONLY("detect_only"),
	HASH("hash"),
	FPE("fpe");

	private final String text;

	RedactType(String text) {
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
