package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemVersionState {
    ACTIVE("active"),
    DEACTIVATED("deactivated"),
    SUSPENDED("suspended"),
    COMPROMISED("compromised"),
    DESTROYED("destroyed"),
    ;

    private final String text;

    ItemVersionState(String text) {
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
