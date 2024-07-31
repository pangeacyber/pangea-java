package cloud.pangeacyber.pangea.share.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ShareLinkOrderBy {
	ID("id"),
	BUCKET_ID("bucket_id"),
	TARGET("target"),
	LINK_TYPE("link_type"),
	ACCESS_COUNT("access_count"),
	MAX_ACCESS_COUNT("max_access_count"),
	CREATED_AT("created_at"),
	EXPIRES_AT("expires_at"),
	LAST_ACCESSED_AT("last_accessed_at"),
	LINK("link");

	private final String text;

	ShareLinkOrderBy(String text) {
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
