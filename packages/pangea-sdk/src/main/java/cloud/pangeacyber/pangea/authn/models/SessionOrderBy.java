package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SessionOrderBy {
	ID("id"),
	CREATED_AT("created_at"),
	TYPE("type"),
	IDENTITY("identity"),
	EMAIL("email"),
	EXPIRE("expire"),
	ACTIVE_TOKEN_ID("active_token_id");

	private final String text;

	SessionOrderBy(String text) {
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
