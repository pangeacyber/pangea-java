package cloud.pangeacyber.pangea.share.models;

import cloud.pangeacyber.pangea.filters.Filter;
import cloud.pangeacyber.pangea.filters.FilterEqual;
import cloud.pangeacyber.pangea.filters.FilterMatch;
import cloud.pangeacyber.pangea.filters.FilterRange;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterList extends Filter {

	private FilterEqual<String> _folder;
	private FilterRange<String> _createdAt;
	private FilterMatch<String> _id;
	private FilterMatch<String> _name;
	private FilterMatch<String> _parentId;
	private FilterRange<Integer> _size;
	private FilterEqual<String[]> _tags;
	private FilterMatch<String> _type;
	private FilterRange<String> _updatedAt;

	public FilterList() {
		_folder = new FilterEqual<String>("folder", this);
		_createdAt = new FilterRange<String>("created_at", this);
		_id = new FilterMatch<String>("id", this);
		_name = new FilterMatch<String>("name", this);
		_parentId = new FilterMatch<String>("parent_id", this);
		_size = new FilterRange<Integer>("size", this);
		_tags = new FilterEqual<String[]>("tags", this);
		_type = new FilterMatch<String>("type", this);
		_updatedAt = new FilterRange<String>("updated_at", this);
	}

	public FilterEqual<String> folder() {
		return _folder;
	}

	public FilterRange<String> createdAt() {
		return _createdAt;
	}

	public FilterMatch<String> id() {
		return _id;
	}

	public FilterMatch<String> name() {
		return _name;
	}

	public FilterMatch<String> parentId() {
		return _parentId;
	}

	public FilterRange<Integer> sizeFilter() {
		return _size;
	}

	public FilterEqual<String[]> tags() {
		return _tags;
	}

	public FilterMatch<String> type() {
		return _type;
	}

	public FilterRange<String> updatedAt() {
		return _updatedAt;
	}
}
