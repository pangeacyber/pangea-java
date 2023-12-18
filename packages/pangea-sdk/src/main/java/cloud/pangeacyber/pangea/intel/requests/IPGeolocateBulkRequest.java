package cloud.pangeacyber.pangea.intel.requests;

public class IPGeolocateBulkRequest extends IPCommonBulkRequest {

	protected IPGeolocateBulkRequest(Builder builder) {
		super(builder);
	}

	public static class Builder extends IPCommonBulkRequest.Builder<Builder> {

		public Builder(String[] ips) {
			this.ips = ips;
		}

		public IPGeolocateBulkRequest build() {
			return new IPGeolocateBulkRequest(this);
		}
	}
}
