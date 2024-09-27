package cloud.pangeacyber.pangea.sanitize.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedactData {

	@JsonProperty("redaction_count")
	@JsonInclude(Include.NON_NULL)
	private Integer redactionCount;

	@JsonProperty("summary_counts")
	@JsonInclude(Include.NON_NULL)
	private Map<String, Integer> summaryCounts;

	/** The scoring result of a set of rules. */
	@JsonProperty("recognizer_results")
	@JsonInclude(Include.NON_NULL)
	private List<RedactRecognizerResult> recognizerResults;

	public RedactData() {}

	public Integer getRedactionCount() {
		return redactionCount;
	}

	public Map<String, Integer> getSummaryCounts() {
		return summaryCounts;
	}

	public List<RedactRecognizerResult> getRecognizerResults() {
		return recognizerResults;
	}
}
