package cloud.pangeacyber.pangea.audit;

public enum TestEnvironment {
    PRODUCTION("PROD"),
    DEVELOP("DEV"),
    STAGING("STG"),
    ;

    private final String text;

    TestEnvironment(String text) {
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
