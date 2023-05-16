package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserInviteListOrderBy {
	ID("id"),
	CREATED_AT("created_at"),
	TYPE("type"),
	EMAIL("email"),
	EXPIRE("expire"),
	CALLBACK("callback"),
	STATE("state"),
	INVITER("inviter"),
	INVITE_ORG("invite_org");

	private final String text;

	UserInviteListOrderBy(String text) {
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
