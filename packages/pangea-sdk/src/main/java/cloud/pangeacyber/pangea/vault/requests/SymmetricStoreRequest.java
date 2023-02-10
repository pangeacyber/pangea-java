package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;

public class SymmetricStoreRequest extends CommonStoreRequest{
    @JsonProperty("type")
    ItemType type;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("managed")
    Boolean managed = null;

    @JsonProperty("algorithm")
    SymmetricAlgorithm algorithm = null;

    @JsonProperty("key")
    String encodedSymmetricKey;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("purpose")
    KeyPurpose purpose = null;

    public SymmetricStoreRequest(SymmetricStoreRequestBuilder builder){
        super(builder);
        this.type = builder.type;
        this.managed = builder.managed;
        this.algorithm = builder.algorithm;
        this.encodedSymmetricKey = builder.encodedSymmetricKey;
        this.purpose = builder.purpose;
    }

    public static class SymmetricStoreRequestBuilder extends CommonStoreRequestBuilder<SymmetricStoreRequestBuilder>{
        ItemType type;
        Boolean managed = null;
        SymmetricAlgorithm algorithm = null;
        String encodedSymmetricKey;
        KeyPurpose purpose = null;

        public SymmetricStoreRequestBuilder(SymmetricAlgorithm algorithm, String encodedSymmetricKey) {
            this.type = ItemType.SYMMETRIC_KEY;
            this.algorithm = algorithm;
            this.encodedSymmetricKey = encodedSymmetricKey;
        }

        public SymmetricStoreRequest build(){
            return new SymmetricStoreRequest(this);
        }


        public SymmetricStoreRequestBuilder setManaged(Boolean managed) {
            this.managed = managed;
            return this;
        }

        public SymmetricStoreRequestBuilder setPurpose(KeyPurpose purpose) {
            this.purpose = purpose;
            return this;
        }

    }

}
