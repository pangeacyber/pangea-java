package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class AsymmetricStoreRequest extends CommonStoreRequest{
    @JsonProperty("algorithm")
    AsymmetricAlgorithm algorithm = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("purpose")
    AsymmetricPurpose purpose = null;

    @JsonProperty("public_key")
    String encodedPublicKey;

    @JsonProperty("private_key")
    String encodedPrivateKey = null;

    public AsymmetricStoreRequest(AsymmetricAlgorithm algorithm, AsymmetricPurpose purpose, String encodedPublicKey, String encodedPrivateKey, Boolean managed, String name, String folder, Metadata metadata, Tags tags,
            Boolean autoRotate, String rotationPolicy, Boolean retainPreviousVersion, String expiration) {
        super(ItemType.ASYMMETRIC_KEY, managed, name, folder, metadata, tags, autoRotate, rotationPolicy, retainPreviousVersion, expiration);
        this.algorithm = algorithm;
        this.purpose = purpose;
        this.encodedPublicKey = encodedPublicKey;
        this.encodedPrivateKey = encodedPrivateKey;
    }

    public AsymmetricStoreRequest(AsymmetricAlgorithm algorithm, String encodedPublicKey, String encodedPrivateKey) {
        super(ItemType.ASYMMETRIC_KEY);
        this.algorithm = algorithm;
        this.encodedPublicKey = encodedPublicKey;
        this.encodedPrivateKey = encodedPrivateKey;
    }

    public AsymmetricStoreRequest(AsymmetricAlgorithm algorithm, String encodedPublicKey, String encodedPrivateKey, AsymmetricPurpose purpose) {
        super(ItemType.ASYMMETRIC_KEY);
        this.algorithm = algorithm;
        this.encodedPublicKey = encodedPublicKey;
        this.encodedPrivateKey = encodedPrivateKey;
        this.purpose = purpose;
    }

    public AsymmetricAlgorithm getAlgorithm() {
        return algorithm;
    }

    public AsymmetricPurpose getPurpose() {
        return purpose;
    }

    public void setAlgorithm(AsymmetricAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setPurpose(AsymmetricPurpose purpose) {
        this.purpose = purpose;
    }

    public String getEncodedPublicKey() {
        return encodedPublicKey;
    }

    public void setEncodedPublicKey(String encodedPublicKey) {
        this.encodedPublicKey = encodedPublicKey;
    }

    public String getEncodedPrivateKey() {
        return encodedPrivateKey;
    }

    public void setEncodedPrivateKey(String encodedPrivateKey) {
        this.encodedPrivateKey = encodedPrivateKey;
    }

}
