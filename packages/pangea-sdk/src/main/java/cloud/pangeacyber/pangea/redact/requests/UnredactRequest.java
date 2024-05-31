package cloud.pangeacyber.pangea.redact.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnredactRequest extends BaseRequest {

	@JsonProperty("redacted_data")
	Object redactedData;

	@JsonProperty("fpe_context")
	String fpeContext;

	protected UnredactRequest(Builder builder) {
		this.redactedData = builder.redactedData;
		this.fpeContext = builder.fpeContext;
	}

	public Object getRedactedData() {
		return redactedData;
	}

	public String getFpeContext() {
		return fpeContext;
	}

	public static class Builder {

		Object redactedData;
		String fpeContext;

		public Builder(Object redactedData, String fpeContext) {
			this.redactedData = redactedData;
			this.fpeContext = fpeContext;
		}

		public UnredactRequest build() {
			return new UnredactRequest(this);
		}
	}
}
