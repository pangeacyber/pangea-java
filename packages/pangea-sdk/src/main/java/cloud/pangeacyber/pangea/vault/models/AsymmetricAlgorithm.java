package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AsymmetricAlgorithm {
    ED25519("ed25519"),
    RSA("rsa"),
    ;

    private final String text;

    AsymmetricAlgorithm(String text) {
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
