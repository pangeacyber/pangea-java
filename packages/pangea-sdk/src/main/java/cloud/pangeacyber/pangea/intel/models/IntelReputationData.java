package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntelReputationData {
    @JsonProperty("category")
    String[] category;

    @JsonProperty("score")
    int score;

    @JsonProperty("verdict")
    String verdict;

    public String[] getCategory() {
        return category;
    }

    public int getScore() {
        return score;
    }

    public String getVerdict() {
        return verdict;
    }
}
