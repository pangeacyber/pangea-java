package cloud.pangeacyber.pangea.audit.utils;

public class ConsistencyProofItem {

	String hash;
	String membershipProof;

	public ConsistencyProofItem(String hash, String membershipProof) {
		this.hash = hash;
		this.membershipProof = membershipProof;
	}

	public String getHash() {
		return hash;
	}

	public String getProof() {
		return membershipProof;
	}
}
