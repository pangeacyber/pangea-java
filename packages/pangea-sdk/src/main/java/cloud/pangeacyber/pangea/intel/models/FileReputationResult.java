package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonProperty;

final public class FileReputationResult extends IntelReputationResult{
    @JsonProperty("data")
    FileReputationData data;

    public FileReputationData getData() {
        return data;
    }

}
