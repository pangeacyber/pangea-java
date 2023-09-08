package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class URLReputationRequest extends IntelCommonRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("url")
	String url;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("url_list")
	String[] urlList;

	protected URLReputationRequest(Builder builder) {
		super(builder);
		this.url = builder.url;
		this.urlList = builder.urlList;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {
		String url = null;
		String[] urlList = null;

		public Builder(String url) {
			this.url = url;
		}

		public Builder(String[] urlList) {
			this.urlList = urlList;
		}

		public URLReputationRequest build() {
			return new URLReputationRequest(this);
		}
	}

	public String getUrl() {
		return url;
	}

	public String[] getUrlList() {
		return urlList;
	}
}
