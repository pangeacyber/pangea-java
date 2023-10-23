package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataProfile extends FlowUpdateData {

	@JsonProperty("profile")
	private final Profile profile;

	private FlowUpdateDataProfile(Profile profile) {
		this.profile = profile;
	}

	public Profile getProfile() {
		return profile;
	}

	public static class Builder {

		private final Profile profile;

		public Builder(Profile profile) {
			this.profile = profile;
		}

		public FlowUpdateDataProfile build() {
			return new FlowUpdateDataProfile(profile);
		}
	}
}
