package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionListRequest extends CommonSessionListRequest {

	private SessionListRequest(SessionListRequestBuilder builder) {
		super(builder);
	}

	public static class SessionListRequestBuilder extends CommonSessionListRequestBuilder<SessionListRequestBuilder> {

		public SessionListRequestBuilder() {}

		public SessionListRequest build() {
			return new SessionListRequest(this);
		}
	}
}
