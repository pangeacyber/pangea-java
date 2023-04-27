package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPReputationRequest extends IntelCommonRequest {

	@JsonProperty("ip")
	String ip;

	protected IPReputationRequest(Builder builder) {
		super(builder);
		this.ip = builder.ip;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String ip;

		public Builder(String ip) {
			this.ip = ip;
		}

		public IPReputationRequest build() {
			return new IPReputationRequest(this);
		}
	}

	public String getIP() {
		return ip;
	}
}
