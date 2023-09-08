package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.DomainReputationData;
import cloud.pangeacyber.pangea.intel.models.DomainReputationDataItem;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class DomainReputationResult extends IntelCommonResult {

	@JsonProperty("data")
	DomainReputationData data;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("data_details")
	HashMap<String,DomainReputationDataItem> dataDetails = null;

	public DomainReputationData getData() {
		return data;
	}

	public Map<String,DomainReputationDataItem> getDataDetails() {
		return dataDetails;
	}
}
