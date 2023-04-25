package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionListRequest extends CommonSessionListRequest {

	private SessionListRequest(Builder builder) {
		super(builder);
	}

	public static class Builder extends CommonBuilder<Builder> {

		public Builder() {}

		public SessionListRequest build() {
			return new SessionListRequest(this);
		}
	}
}
