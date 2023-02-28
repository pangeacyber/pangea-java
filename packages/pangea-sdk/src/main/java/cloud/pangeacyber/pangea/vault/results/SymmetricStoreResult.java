package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SymmetricStoreResult extends CommonStoreResult{
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("algorithm")
    String algorithm;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("key")
    String encodedSymmetricKey = null;

    public SymmetricStoreResult() {
        super();
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getEncodedSymmetricKey() {
        return encodedSymmetricKey;
    }

}
