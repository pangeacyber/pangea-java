package cloud.pangeacyber.pangea.authn.models;

public enum ListOrder {
    ASC("asc"),
    DESC("desc"),
    ;

    private final String text;

    ListOrder(String text) {
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
