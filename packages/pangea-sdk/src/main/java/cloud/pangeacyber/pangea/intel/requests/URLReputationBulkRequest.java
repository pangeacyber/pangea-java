package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class URLReputationBulkRequest extends IntelCommonRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("urls")
	String[] urls;

	protected URLReputationBulkRequest(Builder builder) {
		super(builder);
		this.urls = builder.urls;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String[] urls = null;

		public Builder(String[] urls) {
			this.urls = urls;
		}

		public URLReputationBulkRequest build() {
			return new URLReputationBulkRequest(this);
		}
	}

	public String[] getUrls() {
		return urls;
	}
}
