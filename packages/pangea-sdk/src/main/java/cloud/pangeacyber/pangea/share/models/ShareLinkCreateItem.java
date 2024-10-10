package cloud.pangeacyber.pangea.share.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareLinkCreateItem {

	/** List of storage IDs */
	@JsonProperty("targets")
	private List<String> targets = new ArrayList<>();

	/** Type of link */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("link_type")
	private LinkType linkType;

	/** The date and time the share link expires. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("expires_at")
	private String expiresAt;

	/** The maximum number of times a user can be authenticated to access the share link. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("max_access_count")
	private Integer maxAccessCount;

	/** A list of authenticators */
	@JsonProperty("authenticators")
	private List<Authenticator> authenticators;

	/** An optional title to use in accessing shares. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("title")
	private String title;

	/** An optional message to use in accessing shares. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("message")
	private String message;

	/** An email address */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("notify_email")
	private String notifyEmail;

	/** A list of user-defined tags */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	private List<String> tags;

	private ShareLinkCreateItem(Builder builder) {
		this.targets = builder.targets;
		this.linkType = builder.linkType;
		this.expiresAt = builder.expiresAt;
		this.maxAccessCount = builder.maxAccessCount;
		this.authenticators = builder.authenticators;
		this.title = builder.title;
		this.message = builder.message;
		this.notifyEmail = builder.notifyEmail;
		this.tags = builder.tags;
	}

	public static class Builder {

		private List<String> targets = new ArrayList<>();
		private LinkType linkType;
		private String expiresAt;
		private Integer maxAccessCount;
		private List<Authenticator> authenticators;
		private String title;
		private String message;
		private String notifyEmail;
		private List<String> tags;

		/** List of storage IDs */
		public Builder targets(List<String> targets) {
			this.targets = targets;
			return this;
		}

		/** Type of link */
		public Builder linkType(LinkType linkType) {
			this.linkType = linkType;
			return this;
		}

		/** The date and time the share link expires. */
		public Builder expiresAt(String expiresAt) {
			this.expiresAt = expiresAt;
			return this;
		}

		/** The maximum number of times a user can be authenticated to access the share link. */
		public Builder maxAccessCount(Integer maxAccessCount) {
			this.maxAccessCount = maxAccessCount;
			return this;
		}

		/** A list of authenticators */
		public Builder authenticators(List<Authenticator> authenticators) {
			this.authenticators = authenticators;
			return this;
		}

		/** An optional title to use in accessing shares. */
		public Builder title(String title) {
			this.title = title;
			return this;
		}

		/** An optional message to use in accessing shares. */
		public Builder message(String message) {
			this.message = message;
			return this;
		}

		/** An email address */
		public Builder notifyEmail(String notifyEmail) {
			this.notifyEmail = notifyEmail;
			return this;
		}

		/** A list of user-defined tags */
		public Builder tags(List<String> tags) {
			this.tags = tags;
			return this;
		}

		public ShareLinkCreateItem build() {
			return new ShareLinkCreateItem(this);
		}
	}

	public List<String> getTargets() {
		return targets;
	}

	public LinkType getLinkType() {
		return linkType;
	}

	public String getExpiresAt() {
		return expiresAt;
	}

	public Integer getMaxAccessCount() {
		return maxAccessCount;
	}

	public List<Authenticator> getAuthenticators() {
		return authenticators;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public String getNotifyEmail() {
		return notifyEmail;
	}

	public List<String> getTags() {
		return tags;
	}
}
