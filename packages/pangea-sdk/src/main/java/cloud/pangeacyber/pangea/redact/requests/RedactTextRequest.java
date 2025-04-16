package cloud.pangeacyber.pangea.redact.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.redact.models.Redaction;
import cloud.pangeacyber.pangea.redact.models.VaultParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonInclude(Include.NON_NULL)
@SuperBuilder
public class RedactTextRequest extends BaseRequest {

	/** The text data to redact */
	@NonNull
	@JsonProperty("text")
	String text;

	/**
	 * Setting this value to true will provide a detailed analysis of the
	 * redacted data and the rules that caused redaction
	 */
	@Builder.Default
	@JsonProperty("debug")
	Boolean debug = null;

	/** An array of redact rule short names */
	@JsonProperty("rules")
	String[] rules;

	/** An array of redact ruleset short names */
	@JsonProperty("rulesets")
	String[] rulesets;

	/**
	 * Setting this value to false will omit the redacted result only returning
	 * count
	 */
	@Builder.Default
	@JsonProperty("return_result")
	Boolean returnResult = null;

	/**
	 * This field allows users to specify the redaction method per rule and its
	 * various parameters.
	 */
	@JsonProperty("redaction_method_overrides")
	Map<String, Redaction> redactionMethodOverrides;

	/** Is this redact call going to be used in an LLM request? */
	@Builder.Default
	@JsonProperty("llm_request")
	Boolean llmRequest = null;

	@JsonProperty("vault_parameters")
	VaultParameters vaultParameters;
}
