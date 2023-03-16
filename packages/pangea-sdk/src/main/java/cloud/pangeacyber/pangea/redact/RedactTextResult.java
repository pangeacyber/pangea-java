package cloud.pangeacyber.pangea.redact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedactTextResult {

  @JsonProperty("count")
  int count;

  @JsonProperty("report")
  RedactDebugReport report;

  @JsonProperty("redacted_text")
  String redactedText;

  public int getCount() {
    return count;
  }

  public RedactDebugReport getReport() {
      return report;
  }
	public String getRedactedText() {
		return redactedText;
	}
}
