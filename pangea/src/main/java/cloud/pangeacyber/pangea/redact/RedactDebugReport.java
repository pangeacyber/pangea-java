package cloud.pangeacyber.pangea.redact;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedactDebugReport {
    @JsonProperty("summary_counts")
    LinkedHashMap<String, Integer> summaryCounts;

    @JsonProperty("recognizer_results")
    RedactRecognizerResult[] recognizerResults;

    public LinkedHashMap<String, Integer> getSummaryCounts() {
        return summaryCounts;
    }

    public RedactRecognizerResult[] getRecognizerResults() {
        return recognizerResults;
    }
}
