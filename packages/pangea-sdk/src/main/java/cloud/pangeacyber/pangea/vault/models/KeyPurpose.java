package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum KeyPurpose {
	/** Key used for creating and verifying cryptographic signatures. */
	SIGNING("signing"),

	/** Key used for encryption and decryption operations. */
	ENCRYPTION("encryption"),

	/**
	 * Key used for signing JSON Web Tokens (JWT), producing a verifiable JSON
	 * Web Signature (JWS).
	 */
	JWT("jwt"),

	/** Key used for Format Preserving Encryption (FPE). */
	FPE("fpe"),

	/**
	 * Key used for Public Key Infrastructure (PKI) functions, such as
	 * certificate lifecycle management and identity verification
	 */
	PKI("pki");

	private final String text;

	KeyPurpose(String text) {
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
