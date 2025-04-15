package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PiiEntityAction {
	DISABLED("disabled"),
	REPORT("report"),
	BLOCK("block"),
	MASK("mask"),
	PARTIAL_MASKING("partial_masking"),
	REPLACEMENT("replacement"),
	HASH("hash"),
	FPE("fpe");

	private final String value;

	PiiEntityAction(final String value) {
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
	public static PiiEntityAction fromValue(final String value) {
		for (var x : PiiEntityAction.values()) {
			if (x.value.equals(value)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Unexpected value '" + value + "'");
	}
}
