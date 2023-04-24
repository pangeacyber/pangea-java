package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IntelCommonRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	String provider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw")
	Boolean raw;

	protected IntelCommonRequest(IntelCommonRequestBuilder<?> builder) {
		this.provider = builder.provider;
		this.verbose = builder.verbose;
		this.raw = builder.raw;
	}

	public static class IntelCommonRequestBuilder<B extends IntelCommonRequestBuilder<B>> {

		String provider;
		Boolean verbose;
		Boolean raw;

		public IntelCommonRequest build() {
			return new IntelCommonRequest(this);
		}

		@SuppressWarnings("unchecked")
		final B self() {
			return (B) this;
		}

		public B setProvider(String provider) {
			this.provider = provider;
			return self();
		}

		public B setVerbose(Boolean verbose) {
			this.verbose = verbose;
			return self();
		}

		public B setRaw(Boolean raw) {
			this.raw = raw;
			return self();
		}
	}
}
