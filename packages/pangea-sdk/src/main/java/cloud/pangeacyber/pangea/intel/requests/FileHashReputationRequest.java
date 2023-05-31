package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileHashReputationRequest extends IntelCommonRequest {

	@JsonProperty("hash")
	String hash;

	@JsonProperty("hash_type")
	String hashType;

	protected FileHashReputationRequest(Builder builder) {
		super(builder);
		this.hash = builder.hash;
		this.hashType = builder.hashType;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String hash;
		String hashType;

		public Builder(String hash, String hashType) {
			this.hash = hash;
			this.hashType = hashType;
		}

		public FileHashReputationRequest build() {
			return new FileHashReputationRequest(this);
		}
	}

	public String getHash() {
		return hash;
	}

	public String getHashType() {
		return hashType;
	}
}
