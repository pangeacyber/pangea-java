package cloud.pangeacyber.pangea.prompt_guard.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.prompt_guard.models.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
@Value
public final class GuardRequest extends BaseRequest {

	/**
	 * Prompt content and role array. The content is the text that will be
	 * analyzed for redaction.
	 */
	@NonNull
	List<Message> messages;

	/** Specific analyzers to be used in the call */
	List<String> analyzers;

	/** Boolean to enable classification of the content */
	boolean classify;

	/**
	 * Threshold for the confidence score to consider the prompt as malicious
	 */
	double threshold;
}
