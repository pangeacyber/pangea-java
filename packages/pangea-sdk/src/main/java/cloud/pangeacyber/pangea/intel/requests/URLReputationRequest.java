package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class URLReputationRequest extends IntelCommonRequest {

	@JsonProperty("url")
	String url;

	protected URLReputationRequest(Builder builder) {
		super(builder);
		this.url = builder.url;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String url;

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
