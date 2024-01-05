package cloud.pangeacyber.pangea.store.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ArchiveFormat {
	TAR("tar"),
	ZIP("zip");

	private final String text;

	ArchiveFormat(String text) {
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
