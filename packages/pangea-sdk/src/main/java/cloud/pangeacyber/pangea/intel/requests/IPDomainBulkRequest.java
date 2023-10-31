package cloud.pangeacyber.pangea.intel.requests;

public class IPDomainBulkRequest extends IPCommonBulkRequest {

	protected IPDomainBulkRequest(Builder builder) {
		super(builder);
	}

	public static class Builder extends IPCommonBulkRequest.Builder<Builder> {

		public Builder(String[] ips) {
			this.ips = ips;
		}

		public IPDomainBulkRequest build() {
			return new IPDomainBulkRequest(this);
		}
	}
}
