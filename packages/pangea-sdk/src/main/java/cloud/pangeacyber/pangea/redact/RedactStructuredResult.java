package cloud.pangeacyber.pangea.redact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedactStructuredResult {

	@JsonProperty("count")
	int count;

	@JsonProperty("report")
	RedactDebugReport report;

	@JsonProperty("redacted_data")
	Object redactedData;

	public int getCount() {
		return count;
	}

	public RedactDebugReport getReport() {
		return report;
	}

	public Object getRedactedData() {
		return redactedData;
	}
}
