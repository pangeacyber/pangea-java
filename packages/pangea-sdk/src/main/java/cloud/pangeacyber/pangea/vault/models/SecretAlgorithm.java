package cloud.pangeacyber.pangea.vault.models;

public enum SecretAlgorithm {
    BASE32("base32"),
    ;

    private final String text;

    SecretAlgorithm(String text) {
        this.text = text;
    }

    /* (non-Javadoc)
    * @see java.lang.Enum#toString()
    */
    @Override
    public String toString() {
        return text;
    }
}
