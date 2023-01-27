package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretStoreRequest extends CommonStoreRequest{
    @JsonProperty("secret")
    String secret = null;

    public SecretStoreRequest(String secret, Boolean managed) {
        super(ItemType.SECRET);
        this.secret = secret;
        this.managed = managed;
    }

    public SecretStoreRequest(String secret, Boolean managed, String name, String folder, Metadata metadata, Tags tags, Boolean autoRotate,
            String rotationPolicy, Boolean retainPreviousVersion, String expiration) {
        super(ItemType.SECRET, managed, name, folder, metadata, tags, autoRotate, rotationPolicy, retainPreviousVersion, expiration);
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
