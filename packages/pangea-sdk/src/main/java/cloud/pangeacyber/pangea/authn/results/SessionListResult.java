package cloud.pangeacyber.pangea.authn.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.authn.models.SessionItem;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionListResult {
    @JsonProperty("sessions")
    SessionItem[] sessions;

    public SessionItem[] getSessions() {
        return sessions;
    }

}
