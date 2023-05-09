package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class FileScanResult extends IntelCommonResult {

	@JsonProperty("data")
	FileScanData data;

	public FileScanData getData() {
		return data;
	}
}
