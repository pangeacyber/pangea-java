package cloud.pangeacyber.pangea.intel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpProxyData {
    @JsonProperty("is_proxy")
    boolean isProxy;

    public boolean isProxy() {
        return isProxy;
    }
}
