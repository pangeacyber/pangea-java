package cloud.pangeacyber.pangea.redact.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RedactStructuredRequest {

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
	@JsonProperty("return_result")
	Boolean returnResult = null;

	protected RedactStructuredRequest(RedactStructuredRequestBuilder builder) {
		this.data = builder.data;
		this.jsonp = builder.jsonp;
		this.format = builder.format;
		this.debug = builder.debug;
		this.rules = builder.rules;
		this.returnResult = builder.returnResult;
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

	public Boolean getReturnResult() {
		return returnResult;
	}

	public static class RedactStructuredRequestBuilder {

		Object data;
		String[] jsonp = null;
		String format = null;
		Boolean debug = null;
		String[] rules = null;
		Boolean returnResult = null;

		public RedactStructuredRequestBuilder(Object data) {
			this.data = data;
		}

		public RedactStructuredRequest build() {
			return new RedactStructuredRequest(this);
		}

		public RedactStructuredRequestBuilder setJsonp(String[] jsonp) {
			this.jsonp = jsonp;
			return this;
		}

		public RedactStructuredRequestBuilder setFormat(String format) {
			this.format = format;
			return this;
		}

		public RedactStructuredRequestBuilder setDebug(Boolean debug) {
			this.debug = debug;
			return this;
		}

		public RedactStructuredRequestBuilder setRules(String[] rules) {
			this.rules = rules;
			return this;
		}

		public RedactStructuredRequestBuilder setReturnResult(Boolean returnResult) {
			this.returnResult = returnResult;
			return this;
		}
	}
}
