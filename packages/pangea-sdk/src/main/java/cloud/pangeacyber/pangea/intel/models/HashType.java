package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HashType {
	SHA256("sha256"),
	SHA1("sha1"),
	SHA512("sha512"),
	NTLM("ntlm");

	private final String text;

	HashType(String text) {
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
