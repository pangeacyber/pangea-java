package cloud.pangeacyber.pangea.authz.models;

import cloud.pangeacyber.pangea.filters.Filter;
import cloud.pangeacyber.pangea.filters.FilterMatch;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterTupleList extends Filter {

	private FilterMatch<String> _resourceNamespace;
	private FilterMatch<String> _resourceID;
	private FilterMatch<String> _subjectNamespace;
	private FilterMatch<String> _subjectID;
	private FilterMatch<String> _subjectAction;
	private FilterMatch<String> _relation;

	public FilterTupleList() {
		_resourceNamespace = new FilterMatch<String>("resource_namespace", this);
		_resourceID = new FilterMatch<String>("resource_id", this);
		_subjectNamespace = new FilterMatch<String>("subject_namespace", this);
		_subjectID = new FilterMatch<String>("subject_id", this);
		_subjectAction = new FilterMatch<String>("subject_action", this);
		_relation = new FilterMatch<String>("relation", this);
	}

	public FilterMatch<String> resourceNamespace() {
		return _resourceNamespace;
	}

	public FilterMatch<String> resourceID() {
		return _resourceID;
	}

	public FilterMatch<String> subjectNamespace() {
		return _subjectNamespace;
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
