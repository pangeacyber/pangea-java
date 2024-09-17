package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExportEncryptionType {
	NONE("none"),
	ASYMMETRIC("asymmetric"),
	KEM("kem");

	private final String value;

	ExportEncryptionType(final String value) {
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
	public static ExportEncryptionType fromValue(final String value) {
		for (var x : ExportEncryptionType.values()) {
			if (x.value.equals(value)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Unexpected value '" + value + "'");
	}
}
