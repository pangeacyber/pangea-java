package cloud.pangeacyber.pangea.intel.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.intel.models.FileScanData;

public final class FileScanResult extends IntelCommonResult {

	@JsonProperty("data")
	FileScanData data;

	public FileScanData getData() {
		return data;
	}
}
