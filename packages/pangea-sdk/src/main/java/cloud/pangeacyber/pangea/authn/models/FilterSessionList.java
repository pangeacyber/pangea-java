package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cloud.pangeacyber.pangea.filters.Filter;
import cloud.pangeacyber.pangea.filters.FilterEqual;
import cloud.pangeacyber.pangea.filters.FilterMatch;
import cloud.pangeacyber.pangea.filters.FilterRange;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterSessionList extends Filter {
	private FilterMatch<String> _id;
	private FilterMatch<String> _type;
	private FilterMatch<String> _identity;
	private FilterMatch<String> _email;

	private FilterRange<String> _created_at;
	private FilterRange<String> _expire;

	private FilterEqual<List<String>> _scopes;


	public FilterSessionList() {
		_id = new FilterMatch<String>("id", this);
		_type = new FilterMatch<String>("type", this);
		_identity = new FilterMatch<String>("identity", this);
		_email = new FilterMatch<String>("email", this);

		_created_at = new FilterRange<String>("created_at", this);
		_expire = new FilterRange<String>("expire", this);

		_scopes = new FilterEqual<List<String>>("scopes", this);
	}

	public FilterMatch<String> id() {
		return _id;
	}

	public FilterMatch<String> type() {
		return _type;
	}

	public FilterMatch<String> identity() {
		return _identity;
	}

	public FilterMatch<String> email() {
		return _email;
	}

	public FilterRange<String> created_at() {
		return _created_at;
	}

	public FilterRange<String> expire() {
		return _expire;
	}

	public FilterEqual<List<String>> scopes() {
		return _scopes;
	}

}
