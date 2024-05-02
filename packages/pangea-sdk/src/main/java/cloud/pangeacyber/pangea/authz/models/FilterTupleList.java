package cloud.pangeacyber.pangea.authz.models;

import cloud.pangeacyber.pangea.filters.Filter;
import cloud.pangeacyber.pangea.filters.FilterMatch;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterTupleList extends Filter {

	private FilterMatch<String> _resourceType;
	private FilterMatch<String> _resourceID;
	private FilterMatch<String> _subjectType;
	private FilterMatch<String> _subjectID;
	private FilterMatch<String> _subjectAction;
	private FilterMatch<String> _relation;

	public FilterTupleList() {
		_resourceType = new FilterMatch<String>("resource_type", this);
		_resourceID = new FilterMatch<String>("resource_id", this);
		_subjectType = new FilterMatch<String>("subject_type", this);
		_subjectID = new FilterMatch<String>("subject_id", this);
		_subjectAction = new FilterMatch<String>("subject_action", this);
		_relation = new FilterMatch<String>("relation", this);
	}

	public FilterMatch<String> resourceType() {
		return _resourceType;
	}

	public FilterMatch<String> resourceID() {
		return _resourceID;
	}

	public FilterMatch<String> subjectType() {
		return _subjectType;
	}

	public FilterMatch<String> subjectID() {
		return _subjectID;
	}

	public FilterMatch<String> subjectAction() {
		return _subjectAction;
	}

	public FilterMatch<String> relation() {
		return _relation;
	}
}
