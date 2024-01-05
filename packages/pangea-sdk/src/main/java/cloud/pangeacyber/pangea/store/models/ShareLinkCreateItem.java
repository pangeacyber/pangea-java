package cloud.pangeacyber.pangea.store.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareLinkCreateItem {

	@JsonProperty("targets")
	private List<String> targets = new ArrayList<>();

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("link_type")
	private LinkType linkType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("expires_at")
	private String expiresAt;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("max_access_count")
	private Integer maxAccessCount;

	@JsonProperty("authenticators")
	private List<Authenticator> authenticators;

	private ShareLinkCreateItem(Builder builder) {
		this.targets = builder.targets;
		this.linkType = builder.linkType;
		this.expiresAt = builder.expiresAt;
		this.maxAccessCount = builder.maxAccessCount;
		this.authenticators = builder.authenticators;
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

	public static class Builder {

		private List<String> targets = new ArrayList<>();
		private LinkType linkType;
		private String expiresAt;
		private Integer maxAccessCount;
		private List<Authenticator> authenticators;

		public Builder targets(List<String> targets) {
			this.targets = targets;
			return this;
		}

		public Builder linkType(LinkType linkType) {
			this.linkType = linkType;
			return this;
		}

		public Builder expiresAt(String expiresAt) {
			this.expiresAt = expiresAt;
			return this;
		}

		public Builder maxAccessCount(Integer maxAccessCount) {
			this.maxAccessCount = maxAccessCount;
			return this;
		}

		public Builder authenticators(List<Authenticator> authenticators) {
			this.authenticators = authenticators;
			return this;
		}

		public ShareLinkCreateItem build() {
			return new ShareLinkCreateItem(this);
		}
	}
}
