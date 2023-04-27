package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPVPNRequest extends IntelCommonRequest {

	@JsonProperty("ip")
	String ip;

	protected IPVPNRequest(Builder builder) {
		super(builder);
		this.ip = builder.ip;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String ip;

		public Builder(String ip) {
			this.ip = ip;
		}

		public IPVPNRequest build() {
			return new IPVPNRequest(this);
		}
	}

	public String getIP() {
		return ip;
	}
}
