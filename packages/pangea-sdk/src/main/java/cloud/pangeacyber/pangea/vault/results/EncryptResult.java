package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EncryptResult {
    @JsonProperty("id")
    String id;

    @JsonProperty("version")
    Integer version;

    @JsonProperty("cipher_text")
    String cipherText;

    @JsonProperty("algorithm")
    String algorithm;

    public EncryptResult() {
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getCipherText() {
        return cipherText;
    }

}
