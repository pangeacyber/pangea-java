package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPDomainRequest extends IntelCommonRequest {

	@JsonProperty("ip")
	String ip;

	protected IPDomainRequest(Builder builder) {
		super(builder);
		this.ip = builder.ip;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String ip;

		public Builder(String ip) {
			this.ip = ip;
		}

		public IPDomainRequest build() {
			return new IPDomainRequest(this);
		}
	}

	public String getIP() {
		return ip;
	}
}
