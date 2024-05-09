package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransformAlphabet {
	ALPHA_LOWER("alphalower"),
	ALPHA_UPPER("alphaupper"),
	ALPHANUMERIC("alphanumeric"),
	ALPHANUMERIC_LOWER("alphanumericlower"),
	ALPHANUMERIC_UPPER("alphanumericupper"),
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
