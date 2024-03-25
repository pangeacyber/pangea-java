package cloud.pangeacyber.pangea.sanitize.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SanitizeData {

	@JsonProperty("defang")
	@JsonInclude(Include.NON_NULL)
	private DefangData defang;

	@JsonProperty("redact")
	@JsonInclude(Include.NON_NULL)
	private RedactData redact;

	@JsonProperty("malicious_file")
	@JsonInclude(Include.NON_NULL)
	private Boolean maliciousFile;

	@JsonProperty("cdr")
	@JsonInclude(Include.NON_NULL)
	private CDR cdr;

	public SanitizeData() {}

	public DefangData getDefang() {
		return defang;
	}

	public RedactData getRedact() {
		return redact;
	}

	public Boolean getMaliciousFile() {
		return maliciousFile;
	}

	public CDR getCDR() {
		return cdr;
	}
}
