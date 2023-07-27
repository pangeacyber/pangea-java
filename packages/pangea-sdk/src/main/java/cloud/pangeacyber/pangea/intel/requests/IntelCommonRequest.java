package cloud.pangeacyber.pangea.intel.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IntelCommonRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	String provider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw")
	Boolean raw;

	protected IntelCommonRequest(CommonBuilder<?> builder) {
		this.provider = builder.provider;
		this.verbose = builder.verbose;
		this.raw = builder.raw;
	}

	public static class CommonBuilder<B extends CommonBuilder<B>> {

		String provider;
		Boolean verbose;
		Boolean raw;

		public CommonBuilder() {}

		public IntelCommonRequest build() {
			return new IntelCommonRequest(this);
		}

		@SuppressWarnings("unchecked")
		final B self() {
			return (B) this;
		}

		public B provider(String provider) {
			this.provider = provider;
			return self();
		}

		public B verbose(Boolean verbose) {
			this.verbose = verbose;
			return self();
		}

		public B raw(Boolean raw) {
			this.raw = raw;
			return self();
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
}
