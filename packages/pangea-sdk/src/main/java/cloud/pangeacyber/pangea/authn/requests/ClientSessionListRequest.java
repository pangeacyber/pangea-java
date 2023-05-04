package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientSessionListRequest extends CommonSessionListRequest {

	@JsonProperty("token")
	String token;

	private ClientSessionListRequest(Builder builder) {
		super(builder);
		this.token = builder.token;
	}

	public static class Builder extends CommonBuilder<Builder> {

		String token;

		public Builder(String token) {
			this.token = token;
		}

		public ClientSessionListRequest build() {
			return new ClientSessionListRequest(this);
		}
	}
}
