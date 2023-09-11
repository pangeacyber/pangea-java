package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterUserList extends Filter {

	public String getAcceptedEulaId() {
		return (String) this.get("accepted_eula_id");
	}

	public void setAcceptedEulaId(String acceptedEulaId) {
		this.put("accepted_eula_id", acceptedEulaId);
	}

	@SuppressWarnings("unchecked")
	public List<String> getAcceptedEulaIdContains() {
		return (List<String>) this.get("accepted_eula_id__contains");
	}

	public void setAcceptedEulaIdContains(List<String> acceptedEulaIdContains) {
		this.put("accepted_eula_id__contains", acceptedEulaIdContains);
	}

	@SuppressWarnings("unchecked")
	public List<String> getAcceptedEulaIdIn() {
		return (List<String>) this.get("accepted_eula_id__in");
	}

	public void setAcceptedEulaIdIn(List<String> acceptedEulaIdIn) {
		this.put("accepted_eula_id__in", acceptedEulaIdIn);
	}

	public String getCreatedAt() {
		return (String) this.get("created_at");
	}

	public void setCreatedAt(String createdAt) {
		this.put("created_at", createdAt);
	}

	public String getCreatedAtGt() {
		return (String) this.get("created_at__gt");
	}

	public void setCreatedAtGt(String createdAtGt) {
		this.put("created_at__gt", createdAtGt);
	}

	public String getCreatedAtGte() {
		return (String) this.get("created_at__gte");
	}

	public void setCreatedAtGte(String createdAtGte) {
		this.put("created_at__gte", createdAtGte);
	}

	public String getCreatedAtLt() {
		return (String) this.get("created_at__lt");
	}

	public void setCreatedAtLt(String createdAtLt) {
		this.put("created_at__lt", createdAtLt);
	}

	public String getCreatedAtLte() {
		return (String) this.get("created_at__lte");
	}

	public void setCreatedAtLte(String createdAtLte) {
		this.put("created_at__lte", createdAtLte);
	}

	public Boolean getDisabled() {
		return (Boolean) this.get("disabled");
	}

	public void setDisabled(Boolean disabled) {
		this.put("disabled", disabled);
	}

	public String getEmail() {
		return (String) this.get("email");
	}

	public void setEmail(String email) {
		this.put("email", email);
	}

	@SuppressWarnings("unchecked")
	public List<String> getEmailContains() {
		return (List<String>) this.get("email__contains");
	}

	public void setEmailContains(List<String> emailContains) {
		this.put("email__contains", emailContains);
	}

	public String getId() {
		return (String) this.get("id");
	}

	public void setId(String id) {
		this.put("id", id);
	}

	@SuppressWarnings("unchecked")
	public List<String> getIdContains() {
		return (List<String>) this.get("id__contains");
	}

	public void setIdContains(List<String> idContains) {
		this.put("id__contains", idContains);
	}

	public String getLastLoginAt() {
		return (String) this.get("last_login_at");
	}

	public void setLastLoginAt(String lastLoginAt) {
		this.put("last_login_at", lastLoginAt);
	}

	public String getLastLoginAtGt() {
		return (String) this.get("last_login_at__gt");
	}

	public void setLastLoginAtGt(String lastLoginAtGt) {
		this.put("last_login_at__gt", lastLoginAtGt);
	}

	public String getLastLoginAtGte() {
		return (String) this.get("last_login_at__gte");
	}

	public void setLastLoginAtGte(String lastLoginAtGte) {
		this.put("last_login_at__gte", lastLoginAtGte);
	}

	public String getLastLoginAtLt() {
		return (String) this.get("last_login_at__lt");
	}

	public void setLastLoginAtLt(String lastLoginAtLt) {
		this.put("last_login_at__lt", lastLoginAtLt);
	}

	public String getLastLoginAtLte() {
		return (String) this.get("last_login_at__lte");
	}

	public void setLastLoginAtLte(String lastLoginAtLte) {
		this.put("last_login_at__lte", lastLoginAtLte);
	}

	public String getLastLoginIp() {
		return (String) this.get("last_login_ip");
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.put("last_login_ip", lastLoginIp);
	}

	@SuppressWarnings("unchecked")
	public List<String> getLastLoginIpContains() {
		return (List<String>) this.get("last_login_ip__contains");
	}

	public void setLastLoginIpContains(List<String> lastLoginIpContains) {
		this.put("last_login_ip__contains", lastLoginIpContains);
	}

	public String getLastLoginCity() {
		return (String) this.get("last_login_city");
	}

	public void setLastLoginCity(String lastLoginCity) {
		this.put("last_login_city", lastLoginCity);
	}

	@SuppressWarnings("unchecked")
	public List<String> getLastLoginCityContains() {
		return (List<String>) this.get("last_login_city__contains");
	}

	public void setLastLoginCityContains(List<String> lastLoginCityContains) {
		this.put("last_login_city__contains", lastLoginCityContains);
	}

	public String getLastLoginCountry() {
		return (String) this.get("last_login_country");
	}

	public void setLastLoginCountry(String lastLoginCountry) {
		this.put("last_login_country", lastLoginCountry);
	}

	@SuppressWarnings("unchecked")
	public List<String> getLastLoginCountryContains() {
		return (List<String>) this.get("last_login_country__contains");
	}

	public void setLastLoginCountryContains(List<String> lastLoginCountryContains) {
		this.put("last_login_country__contains", lastLoginCountryContains);
	}

	public Integer getLoginCount() {
		return (Integer) this.get("login_count");
	}

	public void setLoginCount(Integer loginCount) {
		this.put("login_count", loginCount);
	}

	public Integer getLoginCountGt() {
		return (Integer) this.get("login_count__gt");
	}

	public void setLoginCountGt(Integer loginCountGt) {
		this.put("login_count__gt", loginCountGt);
	}

	public Integer getLoginCountGte() {
		return (Integer) this.get("login_count__gte");
	}

	public void setLoginCountGte(Integer loginCountGte) {
		this.put("login_count__gte", loginCountGte);
	}

	public Integer getLoginCountLt() {
		return (Integer) this.get("login_count__lt");
	}

	public void setLoginCountLt(Integer loginCountLt) {
		this.put("login_count__lt", loginCountLt);
	}

	public Integer getLoginCountLte() {
		return (Integer) this.get("login_count__lte");
	}

	public void setLoginCountLte(Integer loginCountLte) {
		this.put("login_count__lte", loginCountLte);
	}

	public Boolean getRequireMfa() {
		return (Boolean) this.get("require_mfa");
	}

	public void setRequireMfa(Boolean requireMfa) {
		this.put("require_mfa", requireMfa);
	}

	@SuppressWarnings("unchecked")
	public List<String> getScopes() {
		return (List<String>) this.get("scopes");
	}

	public void setScopes(List<String> scopes) {
		this.put("scopes", scopes);
	}

	public Boolean getVerified() {
		return (Boolean) this.get("verified");
	}

	public void setVerified(Boolean verified) {
		this.put("verified", verified);
	}
}
