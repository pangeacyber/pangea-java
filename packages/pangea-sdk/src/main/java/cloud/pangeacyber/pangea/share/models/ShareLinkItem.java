package cloud.pangeacyber.pangea.share.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareLinkItem {

	@JsonProperty("id")
	private String id;

	@JsonProperty("storage_pool_id")
	private String storagePoolId;

	@JsonProperty("targets")
	private List<String> targets;

	@JsonProperty("link_type")
	private String linkType;

	@JsonProperty("access_count")
	private int accessCount;

	@JsonProperty("max_access_count")
	private int maxAccessCount;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("expires_at")
	private String expiresAt;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last_accessed_at")
	private String lastAccessedAt;

	@JsonProperty("authenticators")
	private List<Authenticator> authenticators;

	@JsonProperty("link")
	private String link;

	public String getID() {
		return id;
	}

	public String getStoragePoolID() {
		return storagePoolId;
	}

	public List<String> getTargets() {
		return targets;
	}

	public String getLinkType() {
		return linkType;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public int getMaxAccessCount() {
		return maxAccessCount;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getExpiresAt() {
		return expiresAt;
	}

	public String getLastAccessedAt() {
		return lastAccessedAt;
	}

	public List<Authenticator> getAuthenticators() {
		return authenticators;
	}

	public String getLink() {
		return link;
	}
}
