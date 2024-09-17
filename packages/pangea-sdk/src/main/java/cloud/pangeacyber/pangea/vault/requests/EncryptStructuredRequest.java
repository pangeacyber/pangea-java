package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Parameters for an encrypt/decrypt structured request.
 *
 * @param <T> Structured data type.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EncryptStructuredRequest<K, V, T extends Map<K, V>> extends BaseRequest {

	/**
	 * The ID of the key to use. It must be an item of type `symmetric_key` or
	 * `asymmetric_key` and purpose `encryption`.
	 */
	@NonNull
	@JsonProperty("id")
	String id;

	/** Structured data for applying bulk operations. */
	@NonNull
	@JsonProperty("structured_data")
	T structuredData;

	/**
	 * A filter expression. It must point to string elements of the
	 * `structured_data` field.
	 */
	@NonNull
	@JsonProperty("filter")
	String filter;

	/** The item version. Defaults to the current version. */
	@JsonInclude(Include.NON_DEFAULT)
	@JsonProperty("version")
	int version;

	/** User provided authentication data. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("additional_data")
	String additionalData;

	/**
	 * Constructor.
	 *
	 * @param id The ID of the key to use. It must be an item of type
	 *   `symmetric_key` or `asymmetric_key` and purpose `encryption`.
	 * @param structuredData Structured data for applying bulk operations.
	 * @param filter A filter expression. It must point to string elements
	 *   of the `structured_data` field.
	 */
	protected EncryptStructuredRequest(String id, T structuredData, String filter) {
		this.id = id;
		this.structuredData = structuredData;
		this.filter = filter;
	}

	public static class Builder<K, V, T extends Map<K, V>> {

		private EncryptStructuredRequest<K, V, T> result;

		/**
		 * Constructor.
		 *
		 * @param id The ID of the key to use. It must be an item of type
		 *   `symmetric_key` or `asymmetric_key` and purpose `encryption`.
		 * @param structuredData Structured data for applying bulk operations.
		 * @param filter A filter expression. It must point to string elements
		 *   of the `structured_data` field.
		 */
		public Builder(String id, T structuredData, String filter) {
			this.result = new EncryptStructuredRequest<>(id, structuredData, filter);
		}

		public EncryptStructuredRequest<K, V, T> build() {
			return this.result;
		}

		/**
		 * Add item version.
		 *
		 * @param version Item version.
		 */
		public Builder<K, V, T> version(int version) {
			this.result.version = version;
			return this;
		}

		/**
		 * Add additional data.
		 *
		 * @param additionalData Additional data.
		 */
		public Builder<K, V, T> additionalData(String additionalData) {
			this.result.additionalData = additionalData;
			return this;
		}
	}
}
