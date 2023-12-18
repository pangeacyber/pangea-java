package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.FileReputationBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class FileReputationBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	FileReputationBulkData data;

	public FileReputationBulkData getData() {
		return data;
	}
}
