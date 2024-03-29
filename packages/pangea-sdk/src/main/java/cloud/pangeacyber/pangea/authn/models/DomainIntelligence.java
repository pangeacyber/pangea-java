package cloud.pangeacyber.pangea.authn.models;

import cloud.pangeacyber.pangea.intel.models.DomainReputationData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainIntelligence {

	@JsonProperty("is_bad")
	private boolean isBad;

	@JsonProperty("reputation")
	private DomainReputationData reputation;

	public boolean isBad() {
		return isBad;
	}

	public DomainReputationData getReputation() {
		return reputation;
	}
}
