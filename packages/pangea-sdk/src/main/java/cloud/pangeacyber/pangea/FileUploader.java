package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.PresignedURLException;

public class FileUploader extends BaseClient {

	public static String serviceName = "FileUploader";

	public FileUploader(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder() {
			super(Config.builder().token("").baseURLTemplate("").build());
		}

		public FileUploader build() {
			return new FileUploader(this);
		}
	}

	public void uploadFile(String url, TransferMethod transferMethod, FileData fileData)
		throws PresignedURLException, PangeaException {
		if (transferMethod == null) {
			throw new PangeaException("transferMethods should not be null", null);
		}

		if (transferMethod == TransferMethod.MULTIPART) {
			throw new PangeaException(
				String.format("%s not supported. Use service client instead", transferMethod),
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
