package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchOutput extends ResultsOutput{
    @JsonProperty("id")
    String id;

    @JsonProperty("expires_at")
    String expiresAt;

    public String getId() {
        return id;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

}
