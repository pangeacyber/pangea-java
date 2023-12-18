package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPCommonBulkRequest extends IntelCommonRequest {

	@JsonProperty("ips")
	String[] ips;

	protected IPCommonBulkRequest() {}

	protected IPCommonBulkRequest(Builder<?> builder) {
		super(builder);
		this.ips = builder.ips;
	}

	public static class Builder<B extends Builder<B>> extends IntelCommonRequest.CommonBuilder<B> {

		String[] ips;

		protected Builder() {}

		public Builder(String[] ips) {
			this.ips = ips;
		}

		public IPCommonBulkRequest build() {
			return new IPCommonBulkRequest(this);
		}
	}

	public String[] getIPs() {
		return ips;
	}
}
