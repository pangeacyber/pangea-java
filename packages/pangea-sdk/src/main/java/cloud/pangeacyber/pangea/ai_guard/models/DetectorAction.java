package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DetectorAction {
	DETECTED("detected"),
	REDACTED("redacted"),
	DEFANGED("defanged"),
	REPORTED("reported"),
	BLOCKED("blocked");

	private final String text;

	DetectorAction(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@JsonValue
	final String value() {
		return text;
	}
}
