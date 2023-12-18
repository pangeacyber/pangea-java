package cloud.pangeacyber.pangea.intel.requests;

public class IPProxyBulkRequest extends IPCommonBulkRequest {

	protected IPProxyBulkRequest(Builder builder) {
		super(builder);
	}

	public static class Builder extends IPCommonBulkRequest.Builder<Builder> {

		public Builder(String[] ips) {
			this.ips = ips;
		}

		public IPProxyBulkRequest build() {
			return new IPProxyBulkRequest(this);
		}
	}
}
