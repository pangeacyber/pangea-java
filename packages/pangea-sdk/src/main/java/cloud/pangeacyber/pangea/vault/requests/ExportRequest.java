package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.ExportEncryptionAlgorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ExportRequest extends BaseRequest {

	/** The ID of the key to use.*/
	@JsonProperty("id")
	private String id;

	/** The item version. Defaults to the current version.*/
	@JsonProperty("version")
	@JsonInclude(Include.NON_NULL)
	private Integer version;

	/** Public key in pem format used to encrypt exported key(s). */
	@JsonProperty("encryption_key")
	@JsonInclude(Include.NON_NULL)
	private String encryptionKey;

	/** The algorithm of the public key. */
	@JsonProperty("encryption_algorithm")
	@JsonInclude(Include.NON_NULL)
	private ExportEncryptionAlgorithm encryptionAlgorithm;

	private ExportRequest(String id, Integer version, String encryptionKey, ExportEncryptionAlgorithm encryptionAlgorithm) {
		this.id = id;
		this.version = version;
		this.encryptionKey = encryptionKey;
		this.encryptionAlgorithm = encryptionAlgorithm;
	}

	public static final class Builder {

		private String id;
		private Integer version;
		private String encryptionKey;
		private ExportEncryptionAlgorithm encryptionAlgorithm;

		public Builder(String id) {
			this.id = id;
		}

		public ExportRequest build() {
			return new ExportRequest(this.id, this.version, this.encryptionKey, this.encryptionAlgorithm);
		}

		public Builder version(int version) {
			this.version = version;
			return this;
		}

		public Builder encryptionKey(String encryptionKey) {
			this.encryptionKey = encryptionKey;
			return this;
		}

		public Builder encryptionAlgorithm(ExportEncryptionAlgorithm encryptionAlgorithm) {
			this.encryptionAlgorithm = encryptionAlgorithm;
			return this;
		}
	}
}
