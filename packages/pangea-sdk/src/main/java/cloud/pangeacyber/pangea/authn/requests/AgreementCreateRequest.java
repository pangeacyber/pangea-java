package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.AgreementType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementCreateRequest extends BaseRequest {

	@JsonProperty("type")
	AgreementType type;

	@JsonProperty("name")
	String name;

	@JsonProperty("text")
	String text;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("active")
	Boolean active;

	private AgreementCreateRequest(Builder builder) {
		this.type = builder.type;
		this.name = builder.name;
		this.text = builder.text;
		this.active = builder.active;
	}

	public static class Builder {

		AgreementType type;
		String name;
		String text;
		Boolean active;

		public Builder(AgreementType type, String name, String text) {
			this.type = type;
			this.name = name;
			this.text = text;
		}

		public AgreementCreateRequest build() {
			return new AgreementCreateRequest(this);
		}

		public Builder setActive(Boolean active) {
			this.active = active;
			return this;
		}
	}
}
