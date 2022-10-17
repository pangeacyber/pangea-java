package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RootOutput {
    @JsonProperty("data")
    Root data;

    public Root getData() {
        return data;
    }
}
