package cloud.pangeacyber.pangea.redact.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.redact.models.RedactionMethodOverrides;
import cloud.pangeacyber.pangea.redact.models.VaultParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RedactStructuredRequest extends BaseRequest {

	@JsonProperty("data")
	Object data;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("jsonp")
	String[] jsonp = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("format")
	String format = null;

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

	protected RedactStructuredRequest(Builder builder) {
		this.data = builder.data;
		this.jsonp = builder.jsonp;
		this.format = builder.format;
		this.debug = builder.debug;
		this.rules = builder.rules;
		this.returnResult = builder.returnResult;
		this.rulesets = builder.rulesets;
		this.redactionMethodOverrides = builder.redactionMethodOverrides;
		this.llmRequest = builder.llmRequest;
		this.vaultParameters = builder.vaultParameters;
	}

	public Object getData() {
		return data;
	}

	public String[] getJsonp() {
		return jsonp;
	}

	public String getFormat() {
		return format;
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

		Object data;
		String[] jsonp = null;
		String format = null;
		Boolean debug = null;
		String[] rules = null;
		String[] rulesets = null;
		Boolean returnResult = null;
		RedactionMethodOverrides redactionMethodOverrides = null;
		Boolean llmRequest = null;
		VaultParameters vaultParameters = null;

		public Builder(Object data) {
			this.data = data;
		}

		public RedactStructuredRequest build() {
			return new RedactStructuredRequest(this);
		}

		public Builder setJsonp(String[] jsonp) {
			this.jsonp = jsonp;
			return this;
		}

		public Builder setFormat(String format) {
			this.format = format;
			return this;
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
}
