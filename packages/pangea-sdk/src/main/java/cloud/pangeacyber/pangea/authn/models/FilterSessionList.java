package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterSessionList extends Filter {

	public String getActiveTokenId() {
		return (String) this.get("active_token_id");
	}

	public void setActiveTokenId(String activeTokenId) {
		this.put("active_token_id", activeTokenId);
	}

	@SuppressWarnings("unchecked")
	public List<String> getActiveTokenIdContains() {
		return (List<String>) this.get("active_token_id__contains");
	}

	public void setActiveTokenIdContains(List<String> activeTokenIdContains) {
		this.put("active_token_id__contains", activeTokenIdContains);
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

	public String getExpire() {
		return (String) this.get("expire");
	}

	public void setExpire(String expire) {
		this.put("expire", expire);
	}

	public String getExpireGt() {
		return (String) this.get("expire__gt");
	}

	public void setExpireGt(String expireGt) {
		this.put("expire__gt", expireGt);
	}

	public String getExpireGte() {
		return (String) this.get("expire__gte");
	}

	public void setExpireGte(String expireGte) {
		this.put("expire__gte", expireGte);
	}

	public String getExpireLt() {
		return (String) this.get("expire__lt");
	}

	public void setExpireLt(String expireLt) {
		this.put("expire__lt", expireLt);
	}

	public String getExpireLte() {
		return (String) this.get("expire__lte");
	}

	public void setExpireLte(String expireLte) {
		this.put("expire__lte", expireLte);
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

	public String getIdentity() {
		return (String) this.get("identity");
	}

	public void setIdentity(String identity) {
		this.put("identity", identity);
	}

	@SuppressWarnings("unchecked")
	public List<String> getIdentityContains() {
		return (List<String>) this.get("identity__contains");
	}

	public void setIdentityContains(List<String> identityContains) {
		this.put("identity__contains", identityContains);
	}

	@SuppressWarnings("unchecked")
	public List<String> getScopes() {
		return (List<String>) this.get("scopes");
	}

	public void setScopes(List<String> scopes) {
		this.put("scopes", scopes);
	}

	public String getType() {
		return (String) this.get("type");
	}

	public void setType(String type) {
		this.put("type", type);
	}

	@SuppressWarnings("unchecked")
	public List<String> getTypeContains() {
		return (List<String>) this.get("type__contains");
	}

	public void setTypeContains(List<String> typeContains) {
		this.put("type__contains", typeContains);
	}
}
