package cloud.pangeacyber.pangea.redact;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedactStructuredOutput {
    @JsonProperty("redacted_data")
    Map<String, Object> redactedData;

    @JsonProperty("report")
    RedactDebugReport report;

    public Map<String, Object> getRedactedData() {
        return redactedData;
    }

    public RedactDebugReport getReport() {
        return report;
    }
}
