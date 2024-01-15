package cloud.pangeacyber.pangea.store.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LinkType {
	EDITOR("editor"),
	UPLOAD("upload"),
	DOWNLOAD("download");

	private final String text;

	LinkType(String text) {
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
