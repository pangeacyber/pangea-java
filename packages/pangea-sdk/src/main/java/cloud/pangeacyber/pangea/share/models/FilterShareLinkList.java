package cloud.pangeacyber.pangea.share.models;

import cloud.pangeacyber.pangea.filters.Filter;
import cloud.pangeacyber.pangea.filters.FilterEqual;
import cloud.pangeacyber.pangea.filters.FilterMatch;
import cloud.pangeacyber.pangea.filters.FilterRange;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterShareLinkList extends Filter {

	private FilterMatch<String> _id;
	private FilterMatch<String> _target;
	private FilterMatch<String> _link_type;
	private FilterMatch<String> _link;
	private FilterMatch<String> _title;
	private FilterMatch<String> _message;
	private FilterMatch<String> _notifyEmail;
	private FilterEqual<String[]> _tags;

	private FilterRange<Integer> _access_count;
	private FilterRange<Integer> _max_access_count;
	private FilterRange<String> _created_at;
	private FilterRange<String> _expires_at;
	private FilterRange<String> _last_accessed_at;

	public FilterShareLinkList() {
		_id = new FilterMatch<String>("id", this);
		_target = new FilterMatch<String>("target", this);
		_link_type = new FilterMatch<String>("link_type", this);
		_link = new FilterMatch<String>("link", this);
		_title = new FilterMatch<String>("title", this);
		_message = new FilterMatch<String>("message", this);
		_notifyEmail = new FilterMatch<String>("notify_email", this);

		_access_count = new FilterRange<Integer>("access_count", this);
		_max_access_count = new FilterRange<Integer>("max_access_count", this);
		_created_at = new FilterRange<String>("created_at", this);
		_expires_at = new FilterRange<String>("expires_at", this);
		_last_accessed_at = new FilterRange<String>("last_accessed_at", this);

		_tags = new FilterEqual<String[]>("tags", this);
	}

	public FilterMatch<String> id() {
		return _id;
	}

	public FilterMatch<String> target() {
		return _target;
	}

	public FilterMatch<String> link_type() {
		return _link_type;
	}

	public FilterMatch<String> link() {
		return _link;
	}

	public FilterMatch<String> title() {
		return _title;
	}

	public FilterMatch<String> message() {
		return _message;
	}

	public FilterMatch<String> notifyEmail() {
		return _notifyEmail;
	}

	public FilterRange<Integer> access_count() {
		return _access_count;
	}

	public FilterRange<Integer> max_access_count() {
		return _max_access_count;
	}

	public FilterRange<String> created_at() {
		return _created_at;
	}

	public FilterRange<String> expires_at() {
		return _expires_at;
	}

	public FilterRange<String> last_accessed_at() {
		return _last_accessed_at;
	}

	public FilterEqual<String[]> tags() {
		return _tags;
	}
}
