package cloud.pangeacyber.pangea.file_scan.requests;

import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;

public class FileScanUploadURLRequest {

	String provider;
	Boolean verbose;
	Boolean raw;
	TransferMethod transferMethod;
	FileParams fileParams;

	protected FileScanUploadURLRequest(Builder builder) {
		this.provider = builder.provider;
		this.verbose = builder.verbose;
		this.raw = builder.raw;
		this.transferMethod = builder.transferMethod;
		this.fileParams = builder.fileParams;
	}

	public static class Builder {

		String provider;
		Boolean verbose;
		Boolean raw;
		TransferMethod transferMethod = TransferMethod.POST_URL;
		FileParams fileParams;

		public Builder() {}

		public FileScanUploadURLRequest build() {
			return new FileScanUploadURLRequest(this);
		}

		public Builder provider(String provider) {
			this.provider = provider;
			return this;
		}

		public Builder verbose(Boolean verbose) {
			this.verbose = verbose;
			return this;
		}

		public Builder raw(Boolean raw) {
			this.raw = raw;
			return this;
		}

		public Builder transferMethod(TransferMethod transferMethod) {
			this.transferMethod = transferMethod;
			return this;
		}

		public Builder fileParams(FileParams fileParams) {
			this.fileParams = fileParams;
			return this;
		}
	}

	public String getProvider() {
		return provider;
	}

	public Boolean getVerbose() {
		return verbose;
	}

	public Boolean getRaw() {
		return raw;
	}

	public TransferMethod getTransferMethod() {
		return transferMethod;
	}

	public FileParams getFileParams() {
		return fileParams;
	}
}
