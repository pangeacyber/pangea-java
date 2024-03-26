package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tuple {

	@JsonProperty("resource")
	private Resource resource;

	@JsonProperty("relation")
	private String relation;

	@JsonProperty("subject")
	private Subject subject;

	public Tuple(Resource resource, String relation, Subject subject) {
		this.resource = resource;
		this.relation = relation;
		this.subject = subject;
	}

	public Tuple() {}

	public Resource getResource() {
		return resource;
	}

	public String getRelation() {
		return relation;
	}

	public Subject getSubject() {
		return subject;
	}
}
