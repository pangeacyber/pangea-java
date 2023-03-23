package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TOTPsecret {
    @JsonProperty("qr_image")
    String qrImage;

    @JsonProperty("secret")
    String secret;

    public String getQrImage() {
        return qrImage;
    }

    public String getSecret() {
        return secret;
    }

}
