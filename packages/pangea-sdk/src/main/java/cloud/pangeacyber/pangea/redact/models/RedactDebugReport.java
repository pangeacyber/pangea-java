package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value
public class RedactDebugReport {

	@JsonProperty("summary_counts")
	Map<String, Integer> summaryCounts;

	/** The scoring result of a rule */
	@JsonProperty("recognizer_results")
	RedactRecognizerResult[] recognizerResults;
}
