package cloud.pangeacyber.pangea.audit.utils;

public class MembershipProofItem {

	String side;
	byte[] hash;

	public MembershipProofItem(String side, byte[] hash) {
		this.side = side;
		this.hash = hash;
	}

	public String getSide() {
		return side;
	}

	public byte[] getHash() {
		return hash;
	}
}
