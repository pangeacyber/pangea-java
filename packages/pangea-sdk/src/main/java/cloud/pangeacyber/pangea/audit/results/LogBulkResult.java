package cloud.pangeacyber.pangea.audit.results;

import cloud.pangeacyber.pangea.audit.LogResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogBulkResult {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("results")
	ArrayList<LogResult> results;

	public ArrayList<LogResult> getResults() {
		return results;
	}
}
