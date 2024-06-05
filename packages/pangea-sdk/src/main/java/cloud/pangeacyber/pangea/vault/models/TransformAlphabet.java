package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

/** Character sets for format-preserving encryption. */
public enum TransformAlphabet {
	/** Lowercase alphabet (a-z). */
	ALPHA_LOWER("alphalower"),

	/** Uppercase alphabet (A-Z). */
	ALPHA_UPPER("alphaupper"),

	/** Alphanumeric (a-z, A-Z, 0-9). */
	ALPHANUMERIC("alphanumeric"),

	/** Lowercase alphabet with numbers (a-z, 0-9). */
	ALPHANUMERIC_LOWER("alphanumericlower"),

	/** Uppercase alphabet with numbers (A-Z, 0-9). */
	ALPHANUMERIC_UPPER("alphanumericupper"),

	/** Numeric (0-9). */
	NUMERIC("numeric");

	private final String value;

	TransformAlphabet(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
