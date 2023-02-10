package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyResult {
    @JsonProperty("id")
    String id;

    @JsonProperty("version")
    Integer version;

    @JsonProperty("algorithm")
    String algorithm;

    @JsonProperty("valid_signature")
    boolean validSignature;

    public VerifyResult() {
    }

    public VerifyResult(String id, Integer version, String algorithm, boolean validSignature) {
        this.id = id;
        this.version = version;
        this.algorithm = algorithm;
        this.validSignature = validSignature;
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

    public boolean isValidSignature() {
        return validSignature;
    }

}
