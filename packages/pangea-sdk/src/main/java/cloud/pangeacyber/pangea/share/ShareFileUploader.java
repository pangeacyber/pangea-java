package cloud.pangeacyber.pangea.share;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.PresignedURLException;

public class ShareFileUploader extends BaseClient {

	public static String serviceName = "ShareFileUploader";

	public ShareFileUploader(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder() {}

		public ShareFileUploader build() {
			return new ShareFileUploader(this);
		}
	}

	public void uploadFile(String url, TransferMethod transferMethod, FileData fileData)
		throws PresignedURLException, PangeaException {
		if (transferMethod == null) {
			throw new PangeaException("transferMethods should not be null", null);
		}

		if (transferMethod == TransferMethod.MULTIPART) {
			throw new PangeaException(
				String.format("%s not supported. Use ShareClient.put() instead", transferMethod),
				null
			);
		}

		if ((transferMethod == TransferMethod.POST_URL) && fileData.getDetails() == null) {
			throw new PangeaException(
				String.format("Should set FileParams in order to use %s transfer method", transferMethod),
				null
			);
		}

		uploadPresignedURL(url, transferMethod, fileData);
	}
}
