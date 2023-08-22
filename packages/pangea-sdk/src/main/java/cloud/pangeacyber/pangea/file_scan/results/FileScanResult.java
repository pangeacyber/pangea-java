package cloud.pangeacyber.pangea.file_scan.results;

import cloud.pangeacyber.pangea.file_scan.models.FileScanData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public final class FileScanResult {

	@JsonProperty("data")
	FileScanData data;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parameters")
	Map<String, Object> parameters;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw_data")
	Map<String, Object> rawData;

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public Map<String, Object> getRawData() {
		return rawData;
	}

	public FileScanData getData() {
		return data;
	}
}
