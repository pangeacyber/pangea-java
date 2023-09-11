package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterAgreementList extends Filter {

	public Boolean getActive() {
		return (Boolean) this.get("active");
	}

	public void setActive(Boolean active) {
		this.put("active", active);
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

	public String getPublishedAt() {
		return (String) this.get("published_at");
	}

	public void setPublishedAt(String publishedAt) {
		this.put("published_at", publishedAt);
	}

	public String getPublishedAtGt() {
		return (String) this.get("published_at__gt");
	}

	public void setPublishedAtGt(String publishedAtGt) {
		this.put("published_at__gt", publishedAtGt);
	}

	public String getPublishedAtGte() {
		return (String) this.get("published_at__gte");
	}

	public void setPublishedAtGte(String publishedAtGte) {
		this.put("published_at__gte", publishedAtGte);
	}

	public String getPublishedAtLt() {
		return (String) this.get("published_at__lt");
	}

	public void setPublishedAtLt(String publishedAtLt) {
		this.put("published_at__lt", publishedAtLt);
	}

	public String getPublishedAtLte() {
		return (String) this.get("published_at__lte");
	}

	public void setPublishedAtLte(String publishedAtLte) {
		this.put("published_at__lte", publishedAtLte);
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

	@SuppressWarnings("unchecked")
	public List<String> getTypeIn() {
		return (List<String>) this.get("type__in");
	}

	public void setTypeIn(List<String> typeIn) {
		this.put("type__in", typeIn);
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

	@SuppressWarnings("unchecked")
	public List<String> getIdIn() {
		return (List<String>) this.get("id__in");
	}

	public void setIdIn(List<String> idIn) {
		this.put("id__in", idIn);
	}

	public String getName() {
		return (String) this.get("name");
	}

	public void setName(String name) {
		this.put("name", name);
	}

	@SuppressWarnings("unchecked")
	public List<String> getNameContains() {
		return (List<String>) this.get("name__contains");
	}

	public void setNameContains(List<String> nameContains) {
		this.put("name__contains", nameContains);
	}

	@SuppressWarnings("unchecked")
	public List<String> getNameIn() {
		return (List<String>) this.get("name__in");
	}

	public void setNameIn(List<String> nameIn) {
		this.put("name__in", nameIn);
	}

	public String getText() {
		return (String) this.get("text");
	}

	public void setText(String text) {
		this.put("text", text);
	}

	@SuppressWarnings("unchecked")
	public List<String> getTextContains() {
		return (List<String>) this.get("text__contains");
	}

	public void setTextContains(List<String> textContains) {
		this.put("text__contains", textContains);
	}

	@SuppressWarnings("unchecked")
	public List<String> getTextIn() {
		return (List<String>) this.get("text__in");
	}

	public void setTextIn(List<String> textIn) {
		this.put("text__in", textIn);
	}
}
