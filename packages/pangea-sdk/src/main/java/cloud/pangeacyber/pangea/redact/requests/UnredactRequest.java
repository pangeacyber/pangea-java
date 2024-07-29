package cloud.pangeacyber.pangea.redact.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnredactRequest<T> extends BaseRequest {

	@JsonProperty("redacted_data")
	T redactedData;

	@JsonProperty("fpe_context")
	String fpeContext;

	protected UnredactRequest(Builder<T> builder) {
		this.redactedData = builder.redactedData;
		this.fpeContext = builder.fpeContext;
	}

	public T getRedactedData() {
		return redactedData;
	}

	public String getFpeContext() {
		return fpeContext;
	}

	public static class Builder<T> {

		T redactedData;
		String fpeContext;

		public Builder(T redactedData, String fpeContext) {
			this.redactedData = redactedData;
			this.fpeContext = fpeContext;
		}

		public UnredactRequest<T> build() {
			return new UnredactRequest<T>(this);
		}
	}
}
