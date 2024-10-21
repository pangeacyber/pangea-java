package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemVersionState {
	ACTIVE("active"),
	DEACTIVATED("deactivated"),
	SUSPENDED("suspended"),
	COMPROMISED("compromised"),
	DESTROYED("destroyed");

	private final String text;

	ItemVersionState(String text) {
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

	@JsonCreator
	public static ItemVersionState fromValue(final String value) {
		for (var x : ItemVersionState.values()) {
			if (x.text.equals(value)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Unexpected value '" + value + "'");
	}
}
