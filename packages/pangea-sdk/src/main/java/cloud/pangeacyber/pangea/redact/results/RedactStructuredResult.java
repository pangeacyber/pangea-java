package cloud.pangeacyber.pangea.redact.results;

import cloud.pangeacyber.pangea.redact.models.RedactDebugReport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value
public class RedactStructuredResult {

	/** Number of redactions present in the data */
	@JsonProperty("count")
	int count;

	/** Describes the decision process for redactions */
	@JsonProperty("report")
	RedactDebugReport report;

	/** The redacted data */
	@JsonProperty("redacted_data")
	Object redactedData;

	/**
	 * If an FPE redaction method returned results, this will be the context
	 * passed to unredact.
	 */
	@JsonProperty("fpe_context")
	String fpeContext;
}
