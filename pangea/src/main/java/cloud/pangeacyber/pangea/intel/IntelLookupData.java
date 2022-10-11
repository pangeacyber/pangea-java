package cloud.pangeacyber.pangea.intel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntelLookupData {
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