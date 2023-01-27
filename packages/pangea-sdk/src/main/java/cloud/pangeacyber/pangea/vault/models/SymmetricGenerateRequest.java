package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SymmetricGenerateRequest extends CommonGenerateRequest{
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("algorithm")
    SymmetricAlgorithm algorithm = null;

    public SymmetricGenerateRequest(SymmetricAlgorithm algorithm, Boolean managed, Boolean store, String name, String folder, Metadata metadata, Tags tags,
            Boolean autoRotate, String rotationPolicy, Boolean retainPreviousVersion,  String expiration) {
        super(ItemType.SYMMETRIC_KEY, name, folder, metadata, tags, autoRotate, rotationPolicy, retainPreviousVersion, store, expiration,
                managed);
        this.algorithm = algorithm;
    }

    public SymmetricGenerateRequest(SymmetricAlgorithm algorithm, Boolean managed, Boolean store) {
        super(ItemType.SYMMETRIC_KEY);
        this.algorithm = algorithm;
        this.managed = managed;
        this.store = store;
    }

    public SymmetricAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(SymmetricAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Boolean getManaged() {
        return managed;
    }

    public void setManaged(Boolean managed) {
        this.managed = managed;
    }

}
