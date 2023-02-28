package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SymmetricGenerateResult extends CommonGenerateResult{
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("algorithm")
    String algorithm;

    public SymmetricGenerateResult() {
        super();
    }

    public String getAlgorithm() {
        return algorithm;
    }

}
