package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.TransformAlphabet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for a decrypt transform request.
 */
public class DecryptTransformRequest extends BaseRequest {

	/** The ID of the key to use. */
	@JsonProperty("id")
	private String id;

	/** A message encrypted by Vault. */
	@JsonProperty("cipher_text")
	private String cipherText;

	/**
	 * User provided tweak string. If not provided, a random string will be
	 * generated and returned. The user must securely store the tweak source
	 * which will be needed to decrypt the data.
	 */
	@JsonProperty("tweak")
	private String tweak;

	/** Set of characters to use for format-preserving encryption (FPE). */
	@JsonProperty("alphabet")
	private TransformAlphabet alphabet;

	/** The item version. Defaults to the current version. */
	@JsonProperty("version")
	@JsonInclude(Include.NON_NULL)
	private Integer version;

	private DecryptTransformRequest(Builder builder) {
		this.id = builder.id;
		this.cipherText = builder.cipherText;
		this.tweak = builder.tweak;
		this.alphabet = builder.alphabet;
		this.version = builder.version;
	}

	public static class Builder {

		private String id;
		private String cipherText;
		private String tweak;
		private TransformAlphabet alphabet;
		private Integer version;

		public Builder(String id, String cipherText, String tweak, TransformAlphabet alphabet) {
			this.id = id;
			this.cipherText = cipherText;
			this.tweak = tweak;
			this.alphabet = alphabet;
		}

		public Builder version(Integer version) {
			this.version = version;
			return this;
		}

		public DecryptTransformRequest build() {
			return new DecryptTransformRequest(this);
		}
	}

	public String getId() {
		return id;
	}

	public String getCipherText() {
		return cipherText;
	}

	public String getTweak() {
		return tweak;
	}

	public TransformAlphabet getAlphabet() {
		return alphabet;
	}

	public Integer getVersion() {
		return version;
	}
}
