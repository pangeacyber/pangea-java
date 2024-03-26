package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ListOrder {
	ASC("asc"),
	DESC("desc");

	private final String text;

	ListOrder(String text) {
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
