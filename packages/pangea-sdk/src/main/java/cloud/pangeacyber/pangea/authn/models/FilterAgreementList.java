package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterAgreementList extends Filter {
	private FilterEqual<Boolean> _active;
	private FilterFullRange<String> _created_at;
	private FilterFullRange<String> _published_at;
	private FilterMatch<String> _type;
	private FilterMatch<String> _id;
	private FilterMatch<String> _name;
	private FilterMatch<String> _text;

	public FilterAgreementList(){
		_active = new FilterEqual<Boolean>("active", this);
		_created_at = new FilterFullRange<String>("created_at", this);
		_published_at = new FilterFullRange<String>("published_at", this);
		_type = new FilterMatch<String>("type", this);
		_id = new FilterMatch<String>("id", this);
		_name = new FilterMatch<String>("name", this);
		_text = new FilterMatch<String>("text", this);
	}

	public FilterEqual<Boolean> active() {
		return _active;
	}

	public FilterFullRange<String> created_at() {
		return _created_at;
	}

	public FilterFullRange<String> published_at() {
		return _published_at;
	}

	public FilterMatch<String> type() {
		return _type;
	}

	public FilterMatch<String> id() {
		return _id;
	}

	public FilterMatch<String> name() {
		return _name;
	}

	public FilterMatch<String> text() {
		return _text;
	}

}
