package cloud.pangeacyber.pangea.sanitize.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedactData {

	@JsonProperty("redaction_count")
	@JsonInclude(Include.NON_NULL)
	private Integer redactionCount;

	@JsonProperty("summary_counts")
	@JsonInclude(Include.NON_NULL)
	private Map<String, Object> summaryCounts;

	public RedactData() {}

	public Integer getRedactionCount() {
		return redactionCount;
	}

	public Map<String, Object> getSummaryCounts() {
		return summaryCounts;
	}
}
