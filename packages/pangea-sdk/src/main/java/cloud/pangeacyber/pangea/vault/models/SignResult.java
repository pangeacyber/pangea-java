package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SignResult {
    @JsonProperty("id")
    String id;

    @JsonProperty("version")
    Integer version;

    @JsonProperty("signature")
    String signature;

    @JsonProperty("algorithm")
    String algorithm;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("public_key")
    String encodedPublicKey = null;

    public SignResult() {
    }

    public SignResult(String id, Integer version, String signature, String algorithm, String encodedPublicKey) {
        this.id = id;
        this.version = version;
        this.signature = signature;
        this.algorithm = algorithm;
        this.encodedPublicKey = encodedPublicKey;
    }

    public SignResult(String id, Integer version, String signature, String algorithm) {
        this.id = id;
        this.version = version;
        this.signature = signature;
        this.algorithm = algorithm;
    }

    public String getEncodedPublicKey() {
        return encodedPublicKey;
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

    public String getSignature() {
        return signature;
    }

}
