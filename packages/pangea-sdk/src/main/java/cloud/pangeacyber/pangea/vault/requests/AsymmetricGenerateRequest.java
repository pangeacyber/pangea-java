package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.vault.models.AsymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.ItemType;

public class AsymmetricGenerateRequest extends CommonGenerateRequest{
    @JsonProperty("type")
    ItemType type;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("algorithm")
    AsymmetricAlgorithm algorithm = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("purpose")
    KeyPurpose purpose = null;

    protected AsymmetricGenerateRequest(AsymmetricGenerateRequestBuilder builder){
        super(builder);
        this.type = builder.type;
        this.algorithm = builder.algorithm;
        this.purpose = builder.purpose;
    };

    public AsymmetricAlgorithm getAlgorithm() {
        return algorithm;
    }

    public KeyPurpose getPurpose() {
        return purpose;
    }

    public static class AsymmetricGenerateRequestBuilder extends CommonGenerateRequestBuilder<AsymmetricGenerateRequestBuilder> {
        ItemType type;
        AsymmetricAlgorithm algorithm = null;
        KeyPurpose purpose = null;

        public AsymmetricGenerateRequestBuilder() {
            super();
            this.type = ItemType.ASYMMETRIC_KEY;
        }

        public AsymmetricGenerateRequest build(){
            return new AsymmetricGenerateRequest(this);
        }

        public AsymmetricGenerateRequestBuilder setAlgorithm(AsymmetricAlgorithm algorithm) {
            this.algorithm = algorithm;
            return this;
        }

        public AsymmetricGenerateRequestBuilder setPurpose(KeyPurpose purpose) {
            this.purpose = purpose;
            return this;
        }
    }

}
