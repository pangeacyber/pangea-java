package cloud.pangeacyber.pangea.authn.models;

public enum SessionOrderBy {
    ID("id"),
    CREATED_AT("created_at"),
    TYPE("type"),
    IDENTITY("identity"),
    EMAIL("email"),
    EXPIRE("expire"),
    ACTIVE_TOKEN_ID("active_token_id"),
    ;

    private final String text;

    SessionOrderBy(String text) {
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
