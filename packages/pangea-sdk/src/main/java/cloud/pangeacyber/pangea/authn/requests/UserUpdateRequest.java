package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	IDProvider id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disabled")
	Boolean disabled;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("unlock")
	Boolean unlock;

	private UserUpdateRequest(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.disabled = builder.disabled;
		this.unlock = builder.unlock;
	}

	public static class Builder {

		IDProvider id;
		String email;
		Boolean disabled;
		Boolean unlock;

		public Builder() {}

		public UserUpdateRequest build() {
			return new UserUpdateRequest(this);
		}

		public Builder setId(IDProvider id) {
			this.id = id;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setDisabled(Boolean disabled) {
			this.disabled = disabled;
			return this;
		}

		public Builder setUnlock(Boolean unlock) {
			this.unlock = unlock;
			return this;
		}
	}
}
