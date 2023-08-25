package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.AgreementInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementListResult {

	@JsonProperty("agreements")
	AgreementInfo[] agreements;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	String last;

	@JsonProperty("count")
	Integer count;

	public AgreementInfo[] getAgreements() {
		return agreements;
	}

	public String getLast() {
		return last;
	}

	public Integer getCount() {
		return count;
	}
}
