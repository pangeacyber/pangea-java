package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetResult extends CommonGetResult{
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("public_key")
    String encodedPublicKey = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("private_key")
    String encodedPrivateKey = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("algorithm")
    String algorithm = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("purpose")
    String purpose = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("key")
    String encodedSymmetricKey = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("secret")
    String secret = null;

    public GetResult() {
        super();
    }

    public String getEncodedPublicKey() {
        return encodedPublicKey;
    }

    public String getEncodedPrivateKey() {
        return encodedPrivateKey;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getEncodedSymmetricKey() {
        return encodedSymmetricKey;
    }

    public String getSecret() {
        return secret;
    }
}
