package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class AsymmetricGenerateRequest extends CommonGenerateRequest{
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("algorithm")
    AsymmetricAlgorithm algorithm = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("purpose")
    AsymmetricPurpose purpose = null;

    public AsymmetricGenerateRequest(AsymmetricAlgorithm algorithm, AsymmetricPurpose purpose) {
        super(ItemType.ASYMMETRIC_KEY);
        this.algorithm = algorithm;
        this.purpose = purpose;
    }

    public AsymmetricGenerateRequest(AsymmetricAlgorithm algorithm, AsymmetricPurpose purpose, Boolean managed, Boolean store, String name, String folder, Metadata metadata, Tags tags,
            Boolean autoRotate, String rotationPolicy, Boolean retainPreviousVersion, String expiration) {
        super(ItemType.ASYMMETRIC_KEY, name, folder, metadata, tags, autoRotate, rotationPolicy, retainPreviousVersion, store, expiration,
                managed);
        this.algorithm = algorithm;
        this.purpose = purpose;
    }

    public AsymmetricGenerateRequest() {
        super(ItemType.ASYMMETRIC_KEY);
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

}
