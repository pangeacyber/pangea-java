package cloud.pangeacyber.pangea.intel.models;

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

	protected UserPasswordBreachedRequest(UserPasswordBreachedRequestBuilder builder) {
		super(builder);
		this.hashPrefix = builder.hashPrefix;
		this.hashType = builder.hashType;
	}

	public static class UserPasswordBreachedRequestBuilder
		extends IntelCommonRequestBuilder<UserPasswordBreachedRequestBuilder> {

		HashType hashType;
		String hashPrefix;

		public UserPasswordBreachedRequestBuilder(HashType hashType, String hashPrefix) {
			this.hashType = hashType;
			this.hashPrefix = hashPrefix;
		}

		public UserPasswordBreachedRequest build() {
			return new UserPasswordBreachedRequest(this);
		}
	}
}
