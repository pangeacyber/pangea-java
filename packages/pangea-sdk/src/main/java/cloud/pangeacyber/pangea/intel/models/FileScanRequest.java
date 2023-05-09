package cloud.pangeacyber.pangea.intel.models;

public class FileScanRequest extends IntelCommonRequest {

	protected FileScanRequest(Builder builder) {
		super(builder);
	}

	public static class Builder extends IntelCommonRequestBuilder<Builder> {

		public Builder() {}

		public FileScanRequest build() {
			return new FileScanRequest(this);
		}
	}
}
