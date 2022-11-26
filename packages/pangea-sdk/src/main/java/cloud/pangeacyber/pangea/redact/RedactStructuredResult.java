package cloud.pangeacyber.pangea.redact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedactStructuredResult {
    @JsonProperty("redacted_data")
    Object redactedData;

    @JsonProperty("report")
    RedactDebugReport report;

    public Object getRedactedData() {
        return redactedData;
    }

    public RedactDebugReport getReport() {
        return report;
    }
}
