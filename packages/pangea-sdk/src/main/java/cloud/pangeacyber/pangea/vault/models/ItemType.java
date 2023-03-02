package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemType {
	ASYMMETRIC_KEY("asymmetric_key"),
	SYMMETRIC_KEY("symmetric_key"),
	SECRET("secret");

	private final String text;

	ItemType(String text) {
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
