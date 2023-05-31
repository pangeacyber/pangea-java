package cloud.pangeacyber.pangea.redact.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RedactTextRequest {

	@JsonProperty("text")
	String text;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("debug")
	Boolean debug = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rules")
	String[] rules = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("return_result")
	Boolean returnResult = null;

	protected RedactTextRequest(Builder builder) {
		this.text = builder.text;
		this.debug = builder.debug;
		this.rules = builder.rules;
		this.returnResult = builder.returnResult;
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

	public Boolean getReturnResult() {
		return returnResult;
	}

	public static class Builder {

		String text;
		Boolean debug = null;
		String[] rules = null;
		Boolean returnResult = null;

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

		public Builder setReturnResult(Boolean returnResult) {
			this.returnResult = returnResult;
			return this;
		}
	}
}
