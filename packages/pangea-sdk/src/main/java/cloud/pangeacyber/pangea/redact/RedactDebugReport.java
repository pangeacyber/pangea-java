package cloud.pangeacyber.pangea.redact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedactDebugReport {

	@JsonProperty("summary_counts")
	Map<String, Integer> summaryCounts;

	@JsonProperty("recognizer_results")
	RedactRecognizerResult[] recognizerResults;

	public Map<String, Integer> getSummaryCounts() {
		return summaryCounts;
	}

	public RedactRecognizerResult[] getRecognizerResults() {
		return recognizerResults;
	}
}
