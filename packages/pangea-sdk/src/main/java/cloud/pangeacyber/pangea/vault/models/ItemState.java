package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

/** Item state. */
public enum ItemState {
	/** Enabled. */
	ENABLED("enabled"),

	/** Disabled. */
	DISABLED("disabled");

	private final String text;

	ItemState(String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 *
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
