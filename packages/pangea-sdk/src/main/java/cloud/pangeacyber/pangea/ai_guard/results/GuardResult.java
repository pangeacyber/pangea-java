package cloud.pangeacyber.pangea.ai_guard.results;

import cloud.pangeacyber.pangea.ai_guard.models.TextGuardDetectors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value
public final class GuardResult {

	/** Result of the recipe analyzing and input prompt. */
	TextGuardDetectors detectors;

	/** Result of the recipe evaluating configured rules */
	Object accessRules;

	/** Whether or not the prompt triggered a block detection. */
	Boolean blocked;

	/**
	 * If an FPE redaction method returned results, this will be the context
	 * passed to unredact.
	 */
	String fpeContext;

	/** Number of tokens counted in the input */
	Double inputTokenCount;

	/** Updated structured prompt. */
	Object output;

	/** Number of tokens counted in the output */
	Double outputTokenCount;

	/** The Recipe that was used. */
	String recipe;

	/** Whether or not the original input was transformed. */
	Boolean transformed;
}
