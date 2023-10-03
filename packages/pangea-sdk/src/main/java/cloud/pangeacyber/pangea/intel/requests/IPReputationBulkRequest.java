package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPReputationBulkRequest extends IntelCommonRequest {

	@JsonProperty("ips")
	String[] ips;

	protected IPReputationBulkRequest(Builder builder) {
		super(builder);
		this.ips = builder.ips;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String[] ips;

		public Builder(String[] ips) {
			this.ips = ips;
		}

		public IPReputationBulkRequest build() {
			return new IPReputationBulkRequest(this);
		}
	}

	public String[] getIPs() {
		return ips;
	}
}
