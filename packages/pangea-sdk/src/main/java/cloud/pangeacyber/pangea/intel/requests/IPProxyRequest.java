package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPProxyRequest extends IntelCommonRequest {

	@JsonProperty("ip")
	String ip;

	protected IPProxyRequest(Builder builder) {
		super(builder);
		this.ip = builder.ip;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String ip;

		public Builder(String ip) {
			this.ip = ip;
		}

		public IPProxyRequest build() {
			return new IPProxyRequest(this);
		}
	}

	public String getIP() {
		return ip;
	}
}
