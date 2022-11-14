package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RootOutput {
    @JsonProperty("data")
    Root root;

    public Root getRoot() {
        return root;
    }
}
