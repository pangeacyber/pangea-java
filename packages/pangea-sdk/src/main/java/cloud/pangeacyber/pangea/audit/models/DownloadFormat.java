package cloud.pangeacyber.pangea.audit.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DownloadFormat {
	/**
	 * JSON.
	 */
	JSON("json"),

	/**
	 * CSV.
	 */
	CSV("csv");

	private final String text;

	DownloadFormat(String text) {
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
