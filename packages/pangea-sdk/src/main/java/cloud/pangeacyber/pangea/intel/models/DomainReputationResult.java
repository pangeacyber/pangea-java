package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonProperty;

final public class DomainReputationResult extends IntelReputationResult{
    @JsonProperty("data")
    DomainReputationData data;

    public DomainReputationData getData() {
        return data;
    }

}
