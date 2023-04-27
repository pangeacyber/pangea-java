package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.FileReputationData;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class FileReputationResult extends IntelCommonResult {

	@JsonProperty("data")
	FileReputationData data;

	public FileReputationData getData() {
		return data;
	}
}
