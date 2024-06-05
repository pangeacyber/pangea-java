package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SymmetricAlgorithm {
	HS256("HS256"),
	HS384("HS384"),
	HS512("HS512"),
	AES128_CFB("AES-CFB-128"),
	AES256_CFB("AES-CFB-256"),
	AES256_GCM("AES-GCM-256"),
	AES128_CBC("AES-CBC-128"),
	AES256_CBC("AES-CBC-256"),

	/** 128-bit encryption using the FF3-1 algorithm. Beta feature. */
	AES128_FF3_1("AES-FF3-1-128-BETA"),

	/** 256-bit encryption using the FF3-1 algorithm. Beta feature. */
	AES256_FF3_1("AES-FF3-1-256-BETA"),

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
