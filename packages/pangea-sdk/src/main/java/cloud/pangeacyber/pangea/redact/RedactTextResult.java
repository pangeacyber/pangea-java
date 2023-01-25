package cloud.pangeacyber.pangea.redact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedactTextResult {

	@JsonProperty("redacted_text")
	String redactedText;

	@JsonProperty("report")
	RedactDebugReport report;

	public String getRedactedText() {
		return redactedText;
	}

	public RedactDebugReport getReport() {
		return report;
	}
}
