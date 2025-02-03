package cloud.pangeacyber.pangea.ai_guard.results;

import cloud.pangeacyber.pangea.ai_guard.models.TextGuardDetectors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public final class TextGuardResult<TMessages> {

	/** Result of the recipe analyzing and input prompt. */
	TextGuardDetectors detectors;

	/** Updated prompt text, if applicable. */
	String promptText;

	/** Updated structured prompt, if applicable. */
	TMessages promptMessages;

	boolean blocked;
}
