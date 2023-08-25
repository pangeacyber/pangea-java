package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementInfo {

	@JsonProperty("type")
	private String type;

	@JsonProperty("id")
	private String id;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("published_at")
	private String publishedAt;

	@JsonProperty("name")
	private String name;

	@JsonProperty("text")
	private String text;

	@JsonProperty("active")
	private boolean active;

	public String getType() {
		return type;
	}

	public String getID() {
		return id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public boolean isActive() {
		return active;
	}
}
