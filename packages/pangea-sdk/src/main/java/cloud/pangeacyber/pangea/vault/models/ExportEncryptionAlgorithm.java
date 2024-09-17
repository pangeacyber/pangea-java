package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

/** Algorithm of an exported public key. */
public enum ExportEncryptionAlgorithm {
	/** RSA 4096-bit key, OAEP padding, SHA512 digest. */
	RSA4096_OAEP_SHA512("RSA-OAEP-4096-SHA512"),
	RSA_NO_PADDING_4096_KEM("RSA-NO-PADDING-4096-KEM");

	private final String text;

	ExportEncryptionAlgorithm(String text) {
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
