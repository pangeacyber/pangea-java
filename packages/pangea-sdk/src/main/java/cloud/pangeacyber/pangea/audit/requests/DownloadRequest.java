package cloud.pangeacyber.pangea.audit.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.audit.models.DownloadFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class DownloadRequest extends BaseRequest {

	/** ID returned by the search API. */
	@JsonProperty("request_id")
	private String requestID;

	/** ID returned by the search API. */
	@JsonProperty("result_id")
	private String resultID;

	/** Format for the records. */
	@JsonProperty("format")
	DownloadFormat format;

	protected DownloadRequest(Builder builder) {
		this.requestID = builder.requestID;
		this.resultID = builder.resultID;
		this.format = builder.format;
	}

	public String getRequestID() {
		return requestID;
	}

	public String getResultID() {
		return resultID;
	}

	public static class Builder {

		String requestID;
		String resultID;
		DownloadFormat format;

		public Builder() {}

		// Legacy constructor from when download results could only be fetched
		// by search result ID.
		public Builder(String resultId) {
			this.resultId(resultId);
		}

		public Builder requestId(String requestId) {
			this.requestID = requestId;
			return this;
		}

		public Builder resultId(String resultId) {
			this.resultID = resultId;
			return this;
		}

		public Builder format(DownloadFormat format) {
			this.format = format;
			return this;
		}

		public DownloadRequest build() {
			return new DownloadRequest(this);
		}
	}
}
