package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.FileScanData;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class FileScanResult extends IntelCommonResult {

	@JsonProperty("data")
	FileScanData data;

	public FileScanData getData() {
		return data;
	}
}
