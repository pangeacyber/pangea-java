package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonRotateResult {
    @JsonProperty("type")
    String type;

    @JsonProperty("id")
    String id = null;

    @JsonProperty("version")
    Integer version = null;

    @JsonProperty("state")
    Integer state = null;

    public CommonRotateResult() {
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public Integer getState() {
        return state;
    }
}
