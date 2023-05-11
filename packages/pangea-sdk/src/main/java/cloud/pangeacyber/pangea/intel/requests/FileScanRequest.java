package cloud.pangeacyber.pangea.intel.requests;

public class FileScanRequest extends IntelCommonRequest {

	protected FileScanRequest(Builder builder) {
		super(builder);
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		public Builder() {}

		public FileScanRequest build() {
			return new FileScanRequest(this);
		}
	}
}
