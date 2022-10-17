package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResultOutput {
    @JsonProperty("count")
    int count;

    @JsonProperty("events")
    SearchEvent[] events;

    @JsonProperty("root")
    Root root;

    public int getCount() {
        return count;
    }

    public SearchEvent[] getEvents() {
        return events;
    }

    public Root getRoot() {
        return root;
    }
}
