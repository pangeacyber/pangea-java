package cloud.pangeacyber.pangea.redact;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedactTextOutput {
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
