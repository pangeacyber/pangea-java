package cloud.pangeacyber.pangea.share.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareLinkItem {

	/** The ID of a share link. */
	@JsonProperty("id")
	private String id;

	/** The ID of a share bucket resource. */
	@JsonProperty("bucket_id")
	private String bucketId;

	/** List of storage IDs */
	@JsonProperty("targets")
	private List<String> targets;

	/** Type of link */
	@JsonProperty("link_type")
	private String linkType;

	/** The number of times a user has authenticated to access the share link. */
	@JsonProperty("access_count")
	private int accessCount;

	/** The maximum number of times a user can be authenticated to access the share link. */
	@JsonProperty("max_access_count")
	private int maxAccessCount;

	/** The date and time the share link was created. */
	@JsonProperty("created_at")
	private String createdAt;

	/** The date and time the share link expires. */
	@JsonProperty("expires_at")
	private String expiresAt;

	/** The date and time the share link was last accessed. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last_accessed_at")
	private String lastAccessedAt;

	/** A list of authenticators */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("authenticators")
	private List<Authenticator> authenticators;

	/** A URL to access the file/folders shared with a link. */
	@JsonProperty("link")
	private String link;

	/** An optional title to use in accessing shares. */
	@JsonProperty("title")
	private String title;

	/** An optional message to use in accessing shares. */
	@JsonProperty("message")
	private String message;

	/** An email address */
	@JsonProperty("notify_email")
	private String notifyEmail;

	/** A list of user-defined tags */
	@JsonProperty("tags")
	private Tags tags;

	/** The ID of a share link. */
	public String getID() {
		return id;
	}

	/** The ID of a share bucket resource. */
	public String getBucketId() {
		return bucketId;
	}

	/** List of storage IDs */
	public List<String> getTargets() {
		return targets;
	}

	/** Type of link */
	public String getLinkType() {
		return linkType;
	}

	/** The number of times a user has authenticated to access the share link. */
	public int getAccessCount() {
		return accessCount;
	}

	/** The maximum number of times a user can be authenticated to access the share link. */
	public int getMaxAccessCount() {
		return maxAccessCount;
	}

	/** The date and time the share link was created. */
	public String getCreatedAt() {
		return createdAt;
	}

	/** The date and time the share link expires. */
	public String getExpiresAt() {
		return expiresAt;
	}

	/** The date and time the share link was last accessed. */
	public String getLastAccessedAt() {
		return lastAccessedAt;
	}

	/** A list of authenticators */
	public List<Authenticator> getAuthenticators() {
		return authenticators;
	}

	/** A URL to access the file/folders shared with a link. */
	public String getLink() {
		return link;
	}

	/** An optional title to use in accessing shares. */
	public String getTitle() {
		return title;
	}

	/** An optional message to use in accessing shares. */
	public String getMessage() {
		return message;
	}

	/** An email address */
	public String getNotifyEmail() {
		return notifyEmail;
	}

	/** A list of user-defined tags */
	public Tags getTags() {
		return tags;
	}
}
