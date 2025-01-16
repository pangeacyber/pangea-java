package cloud.pangeacyber.pangea.prompt_guard.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public final class GuardResult {

	/** Boolean response for if the prompt was considered malicious or not */
	boolean detected;

	/** Type of analysis, either direct or indirect */
	String type;

	/** Prompt Analyzers for identifying and rejecting properties of prompts */
	String analyzer;

	/** Percent of confidence in the detection result, ranging from 0 to 100 */
	int confidence;
}
