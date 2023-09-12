package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterUserList extends Filter {

	private FilterEqual<Boolean> _disabled;
	private FilterEqual<Boolean> _require_mfa;
	private FilterEqual<Boolean> _verified;
	private FilterEqual<List<String>> _scopes;

	private FilterMatch<String> _eula_id;
	private FilterMatch<String> _email;
	private FilterMatch<String> _id;
	private FilterMatch<String> _last_login_ip;
	private FilterMatch<String> _last_login_city;
	private FilterMatch<String> _last_login_country;

	private FilterRange<String> _created_at;
	private FilterRange<String> _last_login_at;
	private FilterRange<Integer> _login_count;

	public FilterUserList(){
		_disabled = new FilterEqual<Boolean>("disabled", this);
		_require_mfa = new FilterEqual<Boolean>("require_mfa", this);
		_verified = new FilterEqual<Boolean>("verified", this);
		_scopes = new FilterEqual<List<String>>("scopes", this);

		_eula_id = new FilterMatch<String>("eula_id", this);
		_email = new FilterMatch<String>("email", this);
		_id = new FilterMatch<String>("id", this);
		_last_login_ip = new FilterMatch<String>("last_login_ip", this);
		_last_login_city = new FilterMatch<String>("last_login_city", this);
		_last_login_country = new FilterMatch<String>("last_login_country", this);

		_created_at = new FilterRange<String>("created_at", this);
		_last_login_at = new FilterRange<String>("last_login_at", this);
		_login_count = new FilterRange<Integer>("login_count", this);
	}

	public FilterEqual<Boolean> disabled() {
		return _disabled;
	}

	public FilterEqual<Boolean> require_mfa() {
		return _require_mfa;
	}

	public FilterEqual<Boolean> verified() {
		return _verified;
	}

	public FilterEqual<List<String>> scopes() {
		return _scopes;
	}

	public FilterMatch<String> eula_id() {
		return _eula_id;
	}

	public FilterMatch<String> email() {
		return _email;
	}

	public FilterMatch<String> id() {
		return _id;
	}

	public FilterMatch<String> last_login_ip() {
		return _last_login_ip;
	}

	public FilterMatch<String> last_login_city() {
		return _last_login_city;
	}

	public FilterMatch<String> last_login_country() {
		return _last_login_country;
	}

	public FilterRange<String> created_at() {
		return _created_at;
	}

	public FilterRange<String> last_login_at() {
		return _last_login_at;
	}

	public FilterRange<Integer> login_count() {
		return _login_count;
	}

}
