package cloud.pangeacyber.pangea.share.models;

import cloud.pangeacyber.pangea.filters.Filter;
import cloud.pangeacyber.pangea.filters.FilterMatch;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterList extends Filter {

	private FilterMatch<String> _folder;

	public FilterList() {
		_folder = new FilterMatch<String>("folder", this);
	}

	public FilterMatch<String> folder() {
		return _folder;
	}
}
