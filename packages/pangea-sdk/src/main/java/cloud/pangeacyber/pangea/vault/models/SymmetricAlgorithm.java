package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SymmetricAlgorithm {
    AES("aes"),
    ;

    private final String text;

    SymmetricAlgorithm(String text) {
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
