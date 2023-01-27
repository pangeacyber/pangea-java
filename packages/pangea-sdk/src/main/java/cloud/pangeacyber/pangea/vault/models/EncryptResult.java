package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public EncryptResult(String id, Integer version, String cipherText, String algorithm) {
        this.id = id;
        this.version = version;
        this.cipherText = cipherText;
        this.algorithm = algorithm;
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
