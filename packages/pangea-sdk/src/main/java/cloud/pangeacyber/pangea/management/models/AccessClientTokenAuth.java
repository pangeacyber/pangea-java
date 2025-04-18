package cloud.pangeacyber.pangea.management.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccessClientTokenAuth {
	CLIENT_SECRET_BASIC("client_secret_basic"),
	CLIENT_SECRET_POST("client_secret_post");

	private final String value;

	AccessClientTokenAuth(final String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static AccessClientTokenAuth fromValue(final String value) {
		for (var x : AccessClientTokenAuth.values()) {
			if (x.value.equals(value)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Unexpected value '" + value + "'");
	}
}
