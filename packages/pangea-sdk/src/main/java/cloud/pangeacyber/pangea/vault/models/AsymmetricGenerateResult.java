package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class AsymmetricGenerateResult extends CommonGenerateResult{
    @JsonProperty("algorithm")
    String algorithm;

    @JsonProperty("public_key")
    String encodedPublicKey;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("private_key")
    String encodedPrivateKey = null;

    public AsymmetricGenerateResult() {
    }

    public AsymmetricGenerateResult(String type, String id, int version, String encodedPublicKey, String algorithm) {
        super(type, id, version);
        this.encodedPublicKey = encodedPublicKey;
        this.algorithm = algorithm;
    }

    public String getEncodedPublicKey() {
        return encodedPublicKey;
    }

    public String getEncodedPrivateKey() {
        return encodedPrivateKey;
    }

    public void setEncodedPublicKey(String encodedPublicKey) {
        this.encodedPublicKey = encodedPublicKey;
    }

    public void setEncodedPrivateKey(String encodedPrivateKey) {
        this.encodedPrivateKey = encodedPrivateKey;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

}
