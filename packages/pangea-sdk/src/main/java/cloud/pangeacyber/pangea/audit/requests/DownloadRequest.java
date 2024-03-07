package cloud.pangeacyber.pangea.audit.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.audit.models.DownloadFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DownloadRequest extends BaseRequest {

	@JsonProperty("result_id")
	String resultID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("format")
	DownloadFormat format;

	protected DownloadRequest(Builder builder) {
		this.resultID = builder.resultID;
		this.format = builder.format;
	}

	public static class Builder {

		String resultID;
		DownloadFormat format;

		public Builder(String id) {
			this.resultID = id;
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
