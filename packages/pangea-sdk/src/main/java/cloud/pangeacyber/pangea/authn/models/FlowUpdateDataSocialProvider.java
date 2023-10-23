package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataSocialProvider extends FlowUpdateData {

	@JsonProperty("social_provider")
	private final String socialProvider;

	@JsonProperty("uri")
	private final String uri;

	private FlowUpdateDataSocialProvider(Builder builder) {
		this.socialProvider = builder.socialProvider;
		this.uri = builder.uri;
	}

	public String getSocialProvider() {
		return socialProvider;
	}

	public String getUri() {
		return uri;
	}

	public static class Builder {

		private final String socialProvider;
		private final String uri;

		public Builder(String socialProvider, String uri) {
			this.socialProvider = socialProvider;
			this.uri = uri;
		}

		public FlowUpdateDataSocialProvider build() {
			return new FlowUpdateDataSocialProvider(this);
		}
	}
}
