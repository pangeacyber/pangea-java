package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CodeDetectionAction {
	REPORT("report"),
	BLOCK("block");

	private final String value;

	CodeDetectionAction(final String value) {
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
	public static CodeDetectionAction fromValue(final String value) {
		for (var x : CodeDetectionAction.values()) {
			if (x.value.equals(value)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Unexpected value '" + value + "'");
	}
}
