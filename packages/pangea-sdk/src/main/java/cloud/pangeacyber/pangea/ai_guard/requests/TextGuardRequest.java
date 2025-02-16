package cloud.pangeacyber.pangea.ai_guard.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
@Value
public final class TextGuardRequest<TMessages> extends BaseRequest {

	/**
	 * Text to be scanned by AI Guard for PII, sensitive data, malicious
	 * content, and other data types defined by the configuration. Supports
	 * processing up to 10KB of text.
	 */
	String text;

	/**
	 * Structured messages data to be scanned by AI Guard for PII, sensitive
	 * data, malicious content, and other data types defined by the
	 * configuration. Supports processing up to 10KB of JSON text.
	 */
	TMessages messages;

	/**
	 * Structured full llm payload data to be scanned by AI Guard for PII,
	 * sensitive data, malicious content, and other data types defined by the
	 * configuration. Supports processing up to 10KB of JSON text.
	 */
	TMessages llmInput;

	/**
	 * Recipe key of a configuration of data types and settings defined in the
	 * Pangea User Console. It specifies the rules that are to be applied to the
	 * text, such as defang malicious URLs.
	 */
	String recipe;

	/**
	 * Setting this value to true will provide a detailed analysis of the text
	 * data
	 */
	boolean debug;
}
