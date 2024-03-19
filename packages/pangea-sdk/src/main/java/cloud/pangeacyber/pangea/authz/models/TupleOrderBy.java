package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TupleOrderBy {
	RESOURCE_NAMESPACE("resource_namespace"),
	RESOURCE_ID("resource_id"),
	RELATION("relation"),
	SUBJECT_NAMESPACE("subject_namespace"),
	SUBJECT_ID("subject_id"),
	SUBJECT_ACTION("subject_action");

	private final String text;

	TupleOrderBy(String text) {
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
