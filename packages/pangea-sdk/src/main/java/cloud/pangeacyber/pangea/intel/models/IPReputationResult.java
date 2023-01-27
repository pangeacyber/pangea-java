package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonProperty;

final public class IPReputationResult extends IntelReputationResult{
    @JsonProperty("data")
    IPReputationData data;

    public IPReputationData getData() {
        return data;
    }

}
