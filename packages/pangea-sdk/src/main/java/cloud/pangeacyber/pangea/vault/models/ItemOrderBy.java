package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemOrderBy {
	TYPE("type"),
	CREATED_AT("created_at"),
	DESTROYED_AT("destroyed_at"),
	IDENTITY("identity"),
	PURPOSE("purpose"),
	EXPIRATION("expiration"),
	LAST_ROTATED("last_rotated"),
	NEXT_ROTATION("next_rotation"),
	NAME("name"),
	FOLDER("folder"),
	VERSION("version");

	private final String text;

	ItemOrderBy(String text) {
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
