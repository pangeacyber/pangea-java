package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.SessionItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientSessionListResult {

	@JsonProperty("sessions")
	SessionItem[] sessions;

	public SessionItem[] getSessions() {
		return sessions;
	}
}
