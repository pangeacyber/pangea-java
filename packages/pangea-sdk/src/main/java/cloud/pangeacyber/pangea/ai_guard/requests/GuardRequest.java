package cloud.pangeacyber.pangea.ai_guard.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.ai_guard.models.Overrides;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Map;
import lombok.Builder;
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
public final class GuardRequest extends BaseRequest {

	/**
	 * 'messages' (required) contains Prompt content and role array in JSON
	 * format. The `content` is the multimodal text or image input that will be
	 * analyzed. Additional properties such as 'tools' may be provided for
	 * analysis.
	 */
	Object input;

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
	@Builder.Default
	Boolean debug = null;

	Overrides overrides;

	/**
	 * Name of source application.
	 */
	String appName;

	/**
	 * Underlying LLM. Example: 'OpenAI'.
	 */
	String llmProvider;

	/**
	 * Model used to perform the event. Example: 'gpt'.
	 */
	String model;

	/**
	 * Model version used to perform the event. Example: '3.5'.
	 */
	String modelVersion;

	/**
	 * Number of tokens in the request.
	 */
	@Builder.Default
	Integer requestTokenCount = null;

	/**
	 * Number of tokens in the response.
	 */
	@Builder.Default
	Integer responseTokenCount = null;

	/**
	 * IP address of user or app or agent.
	 */
	String sourceIp;

	/**
	 * Location of user or app or agent.
	 */
	String sourceLocation;

	/**
	 * For gateway-like integrations with multi-tenant support.
	 */
	String tenantId;

	/** User/Service account id/service account */
	String actorId;

	/** (AIDR) collector instance id. */
	String collectorInstanceId;
}
