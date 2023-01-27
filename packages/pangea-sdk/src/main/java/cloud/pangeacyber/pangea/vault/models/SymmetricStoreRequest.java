package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SymmetricStoreRequest extends CommonStoreRequest{
    @JsonProperty("algorithm")
    SymmetricAlgorithm algorithm = null;

    @JsonProperty("key")
    String encodedSymmetricKey;

    public SymmetricStoreRequest(SymmetricAlgorithm algorithm, String encodedSymmetricKey, Boolean managed, String name, String folder, Metadata metadata, Tags tags,
            Boolean autoRotate, String rotationPolicy, Boolean retainPreviousVersion, String expiration) {
        super(ItemType.SYMMETRIC_KEY, managed, name, folder, metadata, tags, autoRotate, rotationPolicy, retainPreviousVersion, expiration);
        this.algorithm = algorithm;
        this.encodedSymmetricKey = encodedSymmetricKey;
    }

    public SymmetricStoreRequest(SymmetricAlgorithm algorithm, String encodedSymmetricKey, Boolean managed) {
        super(ItemType.SYMMETRIC_KEY);
        this.algorithm = algorithm;
        this.encodedSymmetricKey = encodedSymmetricKey;
        this.managed = managed;
    }

    public SymmetricAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(SymmetricAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getEncodedSymmetricKey() {
        return encodedSymmetricKey;
    }

    public void setEncodedSymmetricKey(String encodedSymmetricKey) {
        this.encodedSymmetricKey = encodedSymmetricKey;
    }

    public Boolean getManaged() {
        return managed;
    }

    public void setManaged(Boolean managed) {
        this.managed = managed;
    }


}
