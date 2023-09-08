package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DomainReputationRequest extends IntelCommonRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("domain")
	String domain;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("domain_list")
	String[] domainList;

	protected DomainReputationRequest(Builder builder) {
		super(builder);
		this.domain = builder.domain;
		this.domainList = builder.domainList;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String domain = null;
		String[] domainList = null;

		public Builder(String domain) {
			this.domain = domain;
		}

		public Builder(String[] domainList) {
			this.domainList = domainList;
		}

		public DomainReputationRequest build() {
			return new DomainReputationRequest(this);
		}
	}

	public String getDomain() {
		return domain;
	}

	public String[] getDomainList() {
		return domainList;
	}
}
