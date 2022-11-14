package cloud.pangeacyber.pangea.redact;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedactStructuredOutput {
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
