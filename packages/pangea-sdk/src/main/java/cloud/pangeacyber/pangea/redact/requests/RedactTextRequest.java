package cloud.pangeacyber.pangea.redact.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.redact.models.RedactionMethodOverrides;
import cloud.pangeacyber.pangea.redact.models.VaultParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RedactTextRequest extends BaseRequest {

	@JsonProperty("text")
	String text;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("debug")
	Boolean debug = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rules")
	String[] rules = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rulesets")
	String[] rulesets = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("return_result")
	Boolean returnResult = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("redaction_method_overrides")
	RedactionMethodOverrides redactionMethodOverrides = null;

	/** Is this redact call going to be used in an LLM request? */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("llm_request")
	Boolean llmRequest = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("vault_parameters")
	VaultParameters vaultParameters = null;

	protected RedactTextRequest(Builder builder) {
		this.text = builder.text;
		this.debug = builder.debug;
		this.rules = builder.rules;
		this.rulesets = builder.rulesets;
		this.returnResult = builder.returnResult;
		this.redactionMethodOverrides = builder.redactionMethodOverrides;
		this.llmRequest = builder.llmRequest;
		this.vaultParameters = builder.vaultParameters;
	}

	public String getText() {
		return text;
	}

	public Boolean getDebug() {
		return debug;
	}

	public String[] getRules() {
		return rules;
	}

	public String[] getRulesets() {
		return rulesets;
	}

	public Boolean getReturnResult() {
		return returnResult;
	}

	public static class Builder {

		String text;
		Boolean debug = null;
		String[] rules = null;
		String[] rulesets = null;
		Boolean returnResult = null;
		RedactionMethodOverrides redactionMethodOverrides = null;
		Boolean llmRequest = null;
		VaultParameters vaultParameters = null;

		public Builder(String text) {
			this.text = text;
		}

		public RedactTextRequest build() {
			return new RedactTextRequest(this);
		}

		public Builder setDebug(Boolean debug) {
			this.debug = debug;
			return this;
		}

		public Builder setRules(String[] rules) {
			this.rules = rules;
			return this;
		}

		public Builder setRulesets(String[] rulesets) {
			this.rulesets = rulesets;
			return this;
		}

		public Builder setReturnResult(Boolean returnResult) {
			this.returnResult = returnResult;
			return this;
		}

		public Builder setRedactionMethodOverrides(RedactionMethodOverrides rmo) {
			this.redactionMethodOverrides = rmo;
			return this;
		}

		public Builder setLLMRequest(Boolean llmRequest) {
			this.llmRequest = llmRequest;
			return this;
		}

		public Builder setVaultParameters(VaultParameters vp) {
			this.vaultParameters = vp;
			return this;
		}
	}

	public RedactionMethodOverrides getRedactionMethodOverrides() {
		return redactionMethodOverrides;
	}
}
