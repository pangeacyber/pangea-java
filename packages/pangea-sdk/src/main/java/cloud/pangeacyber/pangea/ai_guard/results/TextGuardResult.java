package cloud.pangeacyber.pangea.ai_guard.results;

import cloud.pangeacyber.pangea.ai_guard.models.Message;
import cloud.pangeacyber.pangea.ai_guard.models.TextGuardDetectors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class TextGuardResult {

	/** Updated prompt text, if applicable. */
	String promptText;

	/** Updated structured prompt, if applicable. */
	List<Message> promptMessages;

	/** Whether or not the prompt triggered a block detection. */
	boolean blocked;

	/** The Recipe that was used. */
	String recipe;

	/** Result of the recipe analyzing and input prompt. */
	TextGuardDetectors detectors;

	/**
	 * If an FPE redaction method returned results, this will be the context
	 * passed to unredact.
	 */
	String fpeContext;
}
