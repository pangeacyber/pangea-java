package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileHashReputationBulkRequest extends IntelCommonRequest {

	@JsonProperty("hashes")
	String[] hashes;

	@JsonProperty("hash_type")
	String hashType;

	protected FileHashReputationBulkRequest(Builder builder) {
		super(builder);
		this.hashes = builder.hashes;
		this.hashType = builder.hashType;
	}

	public static class Builder extends IntelCommonRequest.CommonBuilder<Builder> {

		String[] hashes;
		String hashType;

		public Builder(String[] hashes, String hashType) {
			this.hashes = hashes;
			this.hashType = hashType;
		}

		public FileHashReputationBulkRequest build() {
			return new FileHashReputationBulkRequest(this);
		}
	}

	public String[] getHashes() {
		return hashes;
	}

	public String getHashType() {
		return hashType;
	}
}
