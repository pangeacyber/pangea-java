package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserListOrderBy {
	ID("id"),
	CREATED_AT("created_at"),
	LAST_LOGIN_AT("last_login_at"),
	EMAIL("email");

	private final String text;

	UserListOrderBy(String text) {
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
