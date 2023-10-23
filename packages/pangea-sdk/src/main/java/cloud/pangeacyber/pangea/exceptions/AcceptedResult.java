package cloud.pangeacyber.pangea.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcceptedResult {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("accepted_status")
	private AcceptedStatus acceptedStatus = null;

	public AcceptedStatus getAcceptedStatus() {
		return acceptedStatus;
	}
}
