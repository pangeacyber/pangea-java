package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.AgreementType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementUpdateRequest extends BaseRequest {

	@JsonProperty("type")
	AgreementType type;

	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("text")
	String text;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("active")
	Boolean active;

	private AgreementUpdateRequest(Builder builder) {
		this.type = builder.type;
		this.id = builder.id;
		this.name = builder.name;
		this.text = builder.text;
		this.active = builder.active;
	}

	public static class Builder {

		AgreementType type;
		String id;
		String name;
		String text;
		Boolean active;

		public Builder(AgreementType type, String id) {
			this.type = type;
			this.id = id;
		}

		public AgreementUpdateRequest build() {
			return new AgreementUpdateRequest(this);
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setText(String text) {
			this.text = text;
			return this;
		}

		public Builder setActive(Boolean active) {
			this.active = active;
			return this;
		}
	}
}
