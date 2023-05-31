package cloud.pangeacyber.pangea.audit.results;

import cloud.pangeacyber.pangea.audit.SearchEvent;
import cloud.pangeacyber.pangea.audit.models.Root;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultsOutput {

	@JsonProperty("count")
	int count;

	@JsonProperty("events")
	SearchEvent[] events;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("root")
	Root root;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("unpublished_root")
	Root unpublishedRoot;

	public int getCount() {
		return count;
	}

	public SearchEvent[] getEvents() {
		return events;
	}

	public Root getRoot() {
		return root;
	}

	public Root getUnpublishedRoot() {
		return unpublishedRoot;
	}
}
