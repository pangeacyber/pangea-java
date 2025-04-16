package cloud.pangeacyber.pangea.management.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccessRegistryGroup {
	AI_GUARD_EDGE("ai-guard-edge"),
	REDACT_EDGE("redact-edge"),
	PRIVATE_CLOUD("private-cloud");

	private final String value;

	AccessRegistryGroup(final String value) {
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
	public static AccessRegistryGroup fromValue(final String value) {
		for (var x : AccessRegistryGroup.values()) {
			if (x.value.equals(value)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Unexpected value '" + value + "'");
	}
}
