package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cloud.pangeacyber.pangea.filters.FilterEqual;
import cloud.pangeacyber.pangea.filters.FilterMatch;
import cloud.pangeacyber.pangea.filters.FilterRange;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterUserInviteList extends Filter {

	private FilterMatch<String> _callback;
	private FilterMatch<String> _email;
	private FilterMatch<String> _id;
	private FilterMatch<String> _invite_org;
	private FilterMatch<String> _inviter;
	private FilterMatch<String> _state;

	private FilterEqual<Boolean> _signup;
	private FilterEqual<Boolean> _require_mfa;

	private FilterRange<String> _expire;
	private FilterRange<String> _created_at;

	public FilterUserInviteList() {
		_callback = new FilterMatch<String>("callback", this);
		_email = new FilterMatch<String>("email", this);
		_id = new FilterMatch<String>("id", this);
		_invite_org = new FilterMatch<String>("invite_org", this);
		_inviter = new FilterMatch<String>("inviter", this);
		_state = new FilterMatch<String>("state", this);

		_signup = new FilterEqual<Boolean>("signup", this);
		_require_mfa = new FilterEqual<Boolean>("require_mfa", this);

		_expire = new FilterRange<String>("expire", this);
		_created_at = new FilterRange<String>("created_at", this);
	}

	public FilterMatch<String> callback() {
		return _callback;
	}

	public FilterMatch<String> email() {
		return _email;
	}

	public FilterMatch<String> id() {
		return _id;
	}

	public FilterMatch<String> invite_org() {
		return _invite_org;
	}

	public FilterMatch<String> inviter() {
		return _inviter;
	}

	public FilterMatch<String> state() {
		return _state;
	}

	public FilterEqual<Boolean> signup() {
		return _signup;
	}

	public FilterEqual<Boolean> require_mfa() {
		return _require_mfa;
	}

	public FilterRange<String> expire() {
		return _expire;
	}

	public FilterRange<String> created_at() {
		return _created_at;
	}

}
