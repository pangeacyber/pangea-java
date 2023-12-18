package cloud.pangeacyber.pangea.intel.requests;

public class IPVPNBulkRequest extends IPCommonBulkRequest {

	protected IPVPNBulkRequest(Builder builder) {
		super(builder);
	}

	public static class Builder extends IPCommonBulkRequest.Builder<Builder> {

		public Builder(String[] ips) {
			this.ips = ips;
		}

		public IPVPNBulkRequest build() {
			return new IPVPNBulkRequest(this);
		}
	}
}
