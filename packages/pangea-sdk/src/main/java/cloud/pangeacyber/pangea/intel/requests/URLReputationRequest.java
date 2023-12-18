package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class URLReputationRequest extends IntelCommonRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("url")
	String url;

	protected URLReputationRequest(Builder builder) {
		super(builder);
		this.url = builder.url;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String url = null;

		public Builder(String url) {
			this.url = url;
		}

		public URLReputationRequest build() {
			return new URLReputationRequest(this);
		}
	}

	public String getUrl() {
		return url;
	}
}
