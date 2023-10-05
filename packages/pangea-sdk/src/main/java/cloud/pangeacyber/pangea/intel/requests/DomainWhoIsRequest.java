package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DomainWhoIsRequest extends IntelCommonRequest {

	@JsonProperty("domain")
	String domain;

	protected DomainWhoIsRequest(Builder builder) {
		super(builder);
		this.domain = builder.domain;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String domain;

		public Builder(String domain) {
			this.domain = domain;
		}

		public DomainWhoIsRequest build() {
			return new DomainWhoIsRequest(this);
		}
	}

	public String getDomain() {
		return domain;
	}
}
