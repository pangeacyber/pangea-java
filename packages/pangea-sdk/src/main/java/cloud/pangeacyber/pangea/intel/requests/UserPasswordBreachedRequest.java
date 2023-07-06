package cloud.pangeacyber.pangea.intel.requests;

import cloud.pangeacyber.pangea.intel.models.HashType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPasswordBreachedRequest extends IntelCommonRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("hash_type")
	HashType hashType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("hash_prefix")
	String hashPrefix;

	protected UserPasswordBreachedRequest(Builder builder) {
		super(builder);
		this.hashPrefix = builder.hashPrefix;
		this.hashType = builder.hashType;
	}

	public static class Builder extends CommonBuilder<Builder> {

		HashType hashType;
		String hashPrefix;

		public Builder(HashType hashType, String hashPrefix) {
			this.hashType = hashType;
			this.hashPrefix = hashPrefix;
		}

		public UserPasswordBreachedRequest build() {
			return new UserPasswordBreachedRequest(this);
		}
	}
}
