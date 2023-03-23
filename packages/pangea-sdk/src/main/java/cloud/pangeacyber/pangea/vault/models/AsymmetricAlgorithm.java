package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AsymmetricAlgorithm {
	ED25519("ED25519"),
	ES256("ES256"),
	ES384("ES384"),
	ES512("ES512"),
	RSA2048_PKCS1V15_SHA256("RSA-PKCS1V15-2048-SHA256"),
	RSA2048_OAEP_SHA256("RSA-OAEP-2048-SHA256"),

	RSA("RSA-PKCS1V15-2048-SHA256");		// deprecated, use RSA2048_PKCS1V15_SHA256 instead

	private final String text;

	AsymmetricAlgorithm(String text) {
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
