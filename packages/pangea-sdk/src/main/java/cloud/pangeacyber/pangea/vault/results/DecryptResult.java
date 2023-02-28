package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecryptResult {
    @JsonProperty("id")
    String id;

    @JsonProperty("plain_text")
    String plainText;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("version")
    Integer version = null;

    @JsonProperty("algorithm")
    String algorithm;

    public DecryptResult() {
    }

    public String getId() {
        return id;
    }

    public String getPlainText() {
        return plainText;
    }

    public Integer getVersion() {
        return version;
    }

    public String getAlgorithm() {
        return algorithm;
    }

}
