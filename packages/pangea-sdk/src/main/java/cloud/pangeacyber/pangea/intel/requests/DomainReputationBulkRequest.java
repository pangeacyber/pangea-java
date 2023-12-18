package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DomainReputationBulkRequest extends IntelCommonRequest {

	@JsonProperty("domains")
	String[] domains;

	protected DomainReputationBulkRequest(Builder builder) {
		super(builder);
		this.domains = builder.domains;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String[] domains = null;

		public Builder(String[] domains) {
			this.domains = domains;
		}

		public DomainReputationBulkRequest build() {
			return new DomainReputationBulkRequest(this);
		}
	}

	public String[] getdomains() {
		return domains;
	}
}
