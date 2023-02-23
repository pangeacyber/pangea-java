package cloud.pangeacyber.pangea.audit.utils;

import cloud.pangeacyber.pangea.audit.EventVerification;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class Verification {

	public static EventVerification verifyMembershipProof(
		String rootHashEnc,
		String nodeHashEnc,
		String membershipProof
	) {
		if (membershipProof == null || rootHashEnc == null || nodeHashEnc == null) {
			return EventVerification.NOT_VERIFIED;
		}

		if (membershipProof.isEmpty()) {
			return rootHashEnc.equals(nodeHashEnc) ? EventVerification.SUCCESS : EventVerification.FAILED;
		}

		byte[] rootHash = Hash.decode(rootHashEnc);
		byte[] nodeHash = Hash.decode(nodeHashEnc);
		MembershipProof proof = Verification.decodeMembershipProof(membershipProof);

		for (MembershipProofItem item : proof) {
			try {
				nodeHash =
					item.getSide().equals("left")
						? Hash.hash(item.getHash(), nodeHash)
						: Hash.hash(nodeHash, item.getHash());
			} catch (IOException e) {
				return EventVerification.FAILED;
			}
		}
		return Arrays.equals(nodeHash, rootHash) ? EventVerification.SUCCESS : EventVerification.FAILED;
	}

	public static MembershipProof decodeMembershipProof(String memProof) {
		MembershipProof proof = new MembershipProof();

		for (String item : memProof.split(",")) {
			String[] parts = item.split(":");
			if (parts.length >= 2) { // This should never happen, but just in case
				String side = parts[0].equals("l") ? "left" : "right";
				MembershipProofItem proofItem = new MembershipProofItem(side, Hash.decode(parts[1]));
				proof.add(proofItem);
			}
		}

		return proof;
	}

	public static ConsistencyProof decodeConsistencyProof(String[] consistencyProof) {
		ConsistencyProof proof = new ConsistencyProof();
		for (String item : consistencyProof) {
			String[] parts = item.split(",", 2);
			if (parts.length >= 2) {
				String[] hashParts = parts[0].split(":");
				if (hashParts.length >= 2) {
					String hash = hashParts[1];
					proof.add(new ConsistencyProofItem(hash, parts[1]));
				}
			}
		}

		return proof;
	}

	public static EventVerification verifyConsistencyProof(
		String currRootHashEnc,
		String prevRootHashEnc,
		ConsistencyProof proof
	) {
		Iterator<ConsistencyProofItem> it = proof.iterator();
		if (!it.hasNext()) {
			return EventVerification.FAILED;
		}

		byte[] prevRootHash = Hash.decode(prevRootHashEnc);
		byte[] rootHash = Hash.decode(it.next().getHash());

		while (it.hasNext()) {
			try {
				rootHash = Hash.hash(Hash.decode(it.next().getHash()), rootHash);
			} catch (IOException e) {
				return EventVerification.FAILED;
			}
		}

		if (!Arrays.equals(rootHash, prevRootHash)) {
			return EventVerification.FAILED;
		}

		it = proof.iterator();
		while (it.hasNext()) {
			ConsistencyProofItem item = it.next();
			if (
				Verification.verifyMembershipProof(currRootHashEnc, item.getHash(), item.getProof()) !=
				EventVerification.SUCCESS
			) {
				return EventVerification.FAILED;
			}
		}
		return EventVerification.SUCCESS;
	}
}
