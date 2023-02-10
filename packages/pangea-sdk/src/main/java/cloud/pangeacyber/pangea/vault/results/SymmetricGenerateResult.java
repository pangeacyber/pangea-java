package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SymmetricGenerateResult extends CommonGenerateResult{
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("algorithm")
    String algorithm;

    @JsonProperty("key")
    String encodedSymmetricKey = null;

    public SymmetricGenerateResult() {
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getEncodedSymmetricKey() {
        return encodedSymmetricKey;
    }

}
