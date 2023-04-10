package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SymmetricAlgorithm {
	HS256("HS256"),
	HS384("HS384"),
	HS512("HS512"),
	AES128_CFB("AES-CFB-128"),
	AES256_CFB("AES-CFB-256"),

	AES("AES-CFB-128"); // deprecated, use AES128_CFB instead

	private final String text;

	SymmetricAlgorithm(String text) {
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
