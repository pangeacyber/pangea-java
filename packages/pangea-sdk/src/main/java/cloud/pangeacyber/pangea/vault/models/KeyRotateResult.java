package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class KeyRotateResult extends CommonRotateResult{
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("public_key")
    String encodedPublicKey = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("private_key")
    String encodedPrivateKey = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("key")
    String encodedSymmetricKey = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("algorithm")
    String algorithm = null;

    public KeyRotateResult() {
    }

    public String getEncodedPublicKey() {
        return encodedPublicKey;
    }

    public String getEncodedPrivateKey() {
        return encodedPrivateKey;
    }

    public String getEncodedSymmetricKey() {
        return encodedSymmetricKey;
    }

    public void setEncodedPublicKey(String encodedPublicKey) {
        this.encodedPublicKey = encodedPublicKey;
    }

    public void setEncodedPrivateKey(String encodedPrivateKey) {
        this.encodedPrivateKey = encodedPrivateKey;
    }

    public void setEncodedSymmetricKey(String encodedSymmetricKey) {
        this.encodedSymmetricKey = encodedSymmetricKey;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

}
