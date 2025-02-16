package cloud.pangeacyber.pangea.prompt_guard.results;

import cloud.pangeacyber.pangea.prompt_guard.models.Classification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value
public final class GuardResult {

	/** Boolean response for if the prompt was considered malicious or not */
	boolean detected;

	/** Type of analysis, either direct or indirect */
	String type;

	/** Prompt Analyzers for identifying and rejecting properties of prompts */
	String analyzer;

	/** Percent of confidence in the detection result, ranging from 0 to 100 */
	int confidence;

	/** Extra information about the detection result */
	String info;

	/** List of classification results with labels and confidence scores */
	List<Classification> classifications;
}
