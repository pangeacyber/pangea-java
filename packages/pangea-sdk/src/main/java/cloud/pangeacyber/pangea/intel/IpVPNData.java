package cloud.pangeacyber.pangea.intel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpVPNData {
    @JsonProperty("is_vpn")
    boolean isVPN;

    public boolean isVPN() {
        return isVPN;
    }

}
