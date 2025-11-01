package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EventType {
	INPUT("input"),
	OUTPUT("output"),
	TOOL_INPUT("tool_input"),
	TOOL_OUTPUT("tool_output"),
	TOOL_LISTING("tool_listing");

	private final String value;

	EventType(final String value) {
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
	public static EventType fromValue(final String value) {
		for (var x : EventType.values()) {
			if (x.value.equals(value)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Unexpected value '" + value + "'");
	}
}
