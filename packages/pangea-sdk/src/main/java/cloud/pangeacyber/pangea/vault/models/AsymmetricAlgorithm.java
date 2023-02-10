package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AsymmetricAlgorithm {
    ED25519("ed25519"),
    RSA("rsa"),
    ES256("es256"),
    ES384("es384"),
    ES512("es512"),
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
