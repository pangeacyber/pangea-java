package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.AgreementType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementDeleteRequest extends BaseRequest {

	@JsonProperty("type")
	AgreementType type;

	@JsonProperty("id")
	String id;

	private AgreementDeleteRequest(Builder builder) {
		this.type = builder.type;
		this.id = builder.id;
	}

	public static class Builder {

		AgreementType type;
		String id;

		public Builder(AgreementType type, String id) {
			this.type = type;
			this.id = id;
		}

		public AgreementDeleteRequest build() {
			return new AgreementDeleteRequest(this);
		}
	}
}
