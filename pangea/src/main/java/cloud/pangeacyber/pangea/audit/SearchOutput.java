package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchOutput {
    @JsonProperty("id")
    String id;

    @JsonProperty("expires_at")
    String expiresAt;

    @JsonProperty("count")
    int count;

    @JsonProperty("root")
    Root root;

    @JsonProperty("events")
    SearchEvent[] events;

    public String getId() {
        return id;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public int getCount() {
        return count;
    }

    public Root getRoot() {
        return root;
    }

    public SearchEvent[] getEvents() {
        return events;
    }
}
