package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AgreementListOrderBy {
	ID("id"),
	CREATED_AT("created_at"),
	NAME("name"),
	TEXT("text");

	private final String text;

	AgreementListOrderBy(String text) {
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
