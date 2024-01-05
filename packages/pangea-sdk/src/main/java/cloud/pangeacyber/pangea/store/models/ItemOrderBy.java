package cloud.pangeacyber.pangea.store.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemOrderBy {
	ID("id"),
	CREATED_AT("created_at"),
	NAME("name"),
	PARENT_ID("parent_id"),
	TYPE("type"),
	UPDATED_AT("updated_at");

	private final String text;

	ItemOrderBy(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@JsonValue
	public String value() {
		return text;
	}
}
