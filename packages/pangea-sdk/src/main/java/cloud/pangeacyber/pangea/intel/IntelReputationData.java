package cloud.pangeacyber.pangea.intel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntelReputationData {
    @JsonProperty("category")
    String[] Category;

    @JsonProperty("score")
    int Score;

    @JsonProperty("verdict")
    String Verdict;

    public String[] getCategory() {
        return Category;
    }

    public int getScore() {
        return Score;
    }

    public String getVerdict() {
        return Verdict;
    }
}
