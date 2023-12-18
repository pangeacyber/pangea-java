package cloud.pangeacyber.pangea.intel.requests;

public class IPReputationBulkRequest extends IPCommonBulkRequest {

	protected IPReputationBulkRequest(Builder builder) {
		super(builder);
	}

	public static class Builder extends IPCommonBulkRequest.Builder<Builder> {

		public Builder(String[] ips) {
			this.ips = ips;
		}

		public IPReputationBulkRequest build() {
			return new IPReputationBulkRequest(this);
		}
	}
}
