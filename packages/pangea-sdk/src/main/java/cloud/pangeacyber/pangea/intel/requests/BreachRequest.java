package cloud.pangeacyber.pangea.intel.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BreachRequest extends BaseRequest {

	/**
	 * The ID of a breach returned by a provider.
	 */
	@JsonProperty("breach_id")
	String breachID;

	/**
	 * Get breach data from this provider.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	String provider;

	/**
	 * Echo the API parameters in the response.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	/**
	 * A token given in the raw response from SpyCloud. Post this back to paginate results.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cursor")
	String cursor;

	/**
	 * This parameter allows you to define the starting point for a date range query on the spycloud_publish_date field.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("start")
	String start;

	/**
	 * This parameter allows you to define the ending point for a date range query on the spycloud_publish_date field.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("end")
	String end;

	/**
	 * Filter for records that match one of the given severities.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("severity")
	String[] severity;

	protected BreachRequest() {}

	protected BreachRequest(BreachedRequestBuilder builder) {
		this.provider = builder.provider;
		this.verbose = builder.verbose;
		this.breachID = builder.breachID;
	}

	public static class BreachedRequestBuilder {

		String provider;
		Boolean verbose;
		String breachID, cursor, start, end;
		String[] severity;

		public BreachedRequestBuilder(String breachID) {
			this.breachID = breachID;
		}

		public BreachRequest build() {
			return new BreachRequest(this);
		}

		public BreachedRequestBuilder provider(String provider) {
			this.provider = provider;
			return this;
		}

		public BreachedRequestBuilder verbose(Boolean verbose) {
			this.verbose = verbose;
			return this;
		}

		public BreachedRequestBuilder cursor(String cursor) {
			this.cursor = cursor;
			return this;
		}

		public BreachedRequestBuilder start(String start) {
			this.start = start;
			return this;
		}

		public BreachedRequestBuilder end(String end) {
			this.end = end;
			return this;
		}

		public BreachedRequestBuilder severity(String[] severity) {
			this.severity = severity;
			return this;
		}
	}

	public String getProvider() {
		return provider;
	}

	public Boolean getVerbose() {
		return verbose;
	}

	public String getBreachID() {
		return breachID;
	}

	public String getCursor() {
		return cursor;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public String[] getSeverity() {
		return severity;
	}
}
