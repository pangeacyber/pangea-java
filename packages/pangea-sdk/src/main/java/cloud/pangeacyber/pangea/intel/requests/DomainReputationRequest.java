package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DomainReputationRequest extends IntelCommonRequest {

	@JsonProperty("domain")
	String domain;

	protected DomainReputationRequest(Builder builder) {
		super(builder);
		this.domain = builder.domain;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String domain;

		public Builder(String domain) {
			this.domain = domain;
		}

		public DomainReputationRequest build() {
			return new DomainReputationRequest(this);
		}
	}

	public String getDomain() {
		return domain;
	}
}
