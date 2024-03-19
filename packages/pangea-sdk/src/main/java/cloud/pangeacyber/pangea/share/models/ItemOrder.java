package cloud.pangeacyber.pangea.share.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemOrder {
	ASC("asc"),
	DESC("desc");

	private final String text;

	ItemOrder(String text) {
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
