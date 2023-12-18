package cloud.pangeacyber.pangea.intel.requests;

import cloud.pangeacyber.pangea.intel.models.HashType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPasswordBreachedBulkRequest extends IntelCommonRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("hash_type")
	HashType hashType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("hash_prefixes")
	String[] hashPrefixes;

	protected UserPasswordBreachedBulkRequest(Builder builder) {
		super(builder);
		this.hashPrefixes = builder.hashPrefixes;
		this.hashType = builder.hashType;
	}

	public static class Builder extends CommonBuilder<Builder> {

		HashType hashType;
		String[] hashPrefixes;

		public Builder(HashType hashType, String[] hashPrefixes) {
			this.hashType = hashType;
			this.hashPrefixes = hashPrefixes;
		}

		public UserPasswordBreachedBulkRequest build() {
			return new UserPasswordBreachedBulkRequest(this);
		}
	}
}
