package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.TransformAlphabet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptTransformRequest extends BaseRequest {

	/** The ID of the key to use.*/
	@JsonProperty("id")
	private String id;

	/** Message to be encrypted.*/
	@JsonProperty("plain_text")
	private String plainText;

	/**
	 * User provided tweak string. If not provided, a random string will be
	 * generated and returned. The user must securely store the tweak source
	 * which will be needed to decrypt the data.
	 */
	@JsonProperty("tweak")
	@JsonInclude(Include.NON_NULL)
	private String tweak;

	/** Set of characters to use for format-preserving encryption (FPE).*/
	@JsonProperty("alphabet")
	private TransformAlphabet alphabet;

	/** The item version. Defaults to the current version.*/
	@JsonProperty("version")
	@JsonInclude(Include.NON_NULL)
	private Integer version;

	private EncryptTransformRequest(Builder builder) {
		this.id = builder.id;
		this.plainText = builder.plainText;
		this.tweak = builder.tweak;
		this.alphabet = builder.alphabet;
		this.version = builder.version;
	}

	public static class Builder {

		private String id;
		private String plainText;
		private String tweak;
		private TransformAlphabet alphabet;
		private Integer version;

		public Builder(String id, String plainText, TransformAlphabet alphabet) {
			this.id = id;
			this.plainText = plainText;
			this.alphabet = alphabet;
		}

		public EncryptTransformRequest build() {
			return new EncryptTransformRequest(this);
		}

		public Builder tweak(String tweak) {
			this.tweak = tweak;
			return this;
		}

		public Builder version(Integer version) {
			this.version = version;
			return this;
		}
	}
}
