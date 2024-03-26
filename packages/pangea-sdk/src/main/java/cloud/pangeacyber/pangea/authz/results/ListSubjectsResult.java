package cloud.pangeacyber.pangea.authz.results;

import cloud.pangeacyber.pangea.authz.models.Subject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListSubjectsResult {

	@JsonProperty("subjects")
	private Subject[] subjects;

	public ListSubjectsResult() {}

	public Subject[] getSubjects() {
		return subjects;
	}
}
