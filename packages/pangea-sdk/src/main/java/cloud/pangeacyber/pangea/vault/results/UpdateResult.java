package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateResult {
    @JsonProperty("id")
    String id;

    public UpdateResult() {
    }

    public String getId() {
        return id;
    }

}
