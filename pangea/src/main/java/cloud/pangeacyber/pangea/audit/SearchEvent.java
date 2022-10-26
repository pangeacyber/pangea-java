package cloud.pangeacyber.pangea.audit;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import cloud.pangeacyber.pangea.audit.arweave.PublishedRoot;
import cloud.pangeacyber.pangea.audit.utils.Hash;
import cloud.pangeacyber.pangea.exceptions.VerificationFailed;


final class MembershipProofItem{
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

final class ConsistencyProofItem{
    byte[] hash;
    MembershipProof proof;

    public ConsistencyProofItem(byte[] hash, MembershipProof proof) {
        this.hash = hash;
        this.proof = proof;
    }

    public byte[] getHash() {
        return hash;
    }

    public MembershipProof getProof() {
        return proof;
    }
}


final class MembershipProof extends LinkedList<MembershipProofItem>{};
final class ConsistencyProof extends LinkedList<ConsistencyProofItem>{};

public class SearchEvent {
    @JsonProperty("envelope")
    EventEnvelope eventEnvelope;

    @JsonProperty("hash")
    String hash;

    @JsonProperty("leaf_index")
    int leafIndex;

    @JsonProperty("membership_proof")
    String membershipProof;

    @JsonIgnore
    EventVerification membershipVerification = EventVerification.NOT_VERIFIED;

    @JsonIgnore
    EventVerification consistencyVerification = EventVerification.NOT_VERIFIED;

    @JsonIgnore
    EventVerification signatureVerification = EventVerification.NOT_VERIFIED;

    public EventEnvelope getEventEnvelope() {
        return eventEnvelope;
    }

    public String getHash() {
        return hash;
    }

    public int getLeafIndex() {
        return leafIndex;
    }

    public String getMembershipProof() {
        return membershipProof;
    }

    public EventVerification getMembershipVerification() {
        return membershipVerification;
    }

    public EventVerification getConsistencyVerification() {
        return consistencyVerification;
    }

    public EventVerification getSignatureVerification() {
        return signatureVerification;
    }

    public void verifyHash() throws VerificationFailed {
        String canonicalJson;
        try{
            canonicalJson = EventEnvelope.canonicalize(this.eventEnvelope);
        } catch(JsonProcessingException e){
            throw new VerificationFailed("Failed to canonicalize envelope in hash verification. Event hash: " + this.hash, e, this.hash);
        }

        String hash = Hash.hash(canonicalJson);
        if(!hash.equals(this.hash)){
            throw new VerificationFailed("Failed hash verification. Calculated and received hash are not equals. Event hash: " + this.hash, null, this.hash);
        }
    }

    public void verifySignature() throws VerificationFailed {
        this.signatureVerification = this.eventEnvelope.verifySignature();
        if(this.signatureVerification == EventVerification.FAILED){
            throw new VerificationFailed("Event signature verification failed. Calculated and received signatures are not equals.  Event hash: " + this.hash, null, this.hash);
        }
    }

    static private ConsistencyProof decodeConsistencyProof(String[] consistencyProof){
        ConsistencyProof proof = new ConsistencyProof();
        for(String item: consistencyProof){
            String[] parts = item.split(",", 2);
            String[] hashParts = parts[0].split(":");
            byte[] hash = Hash.decode(hashParts[1]);

            MembershipProof memProof = SearchEvent.decodeMembershipProof(parts[1]);
            proof.add(new ConsistencyProofItem(hash, memProof));
        }

        return proof;
    }

    public void verifyConsistency(Map<Integer, PublishedRoot> publishedRoots){
        // This should never happen.
        if(leafIndex < 0){
            return;
        }

        if(!publishedRoots.containsKey(leafIndex) || !publishedRoots.containsKey(leafIndex+1)){
            return;
        }

        PublishedRoot currRoot = publishedRoots.get(leafIndex+1);
        PublishedRoot prevRoot = publishedRoots.get(leafIndex);

        byte[] currRootHash = Hash.decode(currRoot.getRootHash());
        byte[] prevRootHash = Hash.decode(prevRoot.getRootHash());
        ConsistencyProof proof = SearchEvent.decodeConsistencyProof(currRoot.getConsistencyProof());

        Iterator<ConsistencyProofItem> it = proof.iterator();
        if(!it.hasNext()){
            return;
        }

        byte[] rootHash = it.next().getHash();
        while(it.hasNext()){
            try{
                rootHash = Hash.hash(it.next().getHash(), rootHash);
            } catch(IOException e){
                this.consistencyVerification = EventVerification.FAILED;
                return; 
            }
        }

        if(!Arrays.equals(rootHash, prevRootHash)){
            this.consistencyVerification = EventVerification.FAILED;
            return; 
        }

        it = proof.iterator();
        while(it.hasNext()){
            ConsistencyProofItem item = it.next();
            if(!SearchEvent.verifyMembershipProof(currRootHash, item.getHash(), item.getProof())){
                this.consistencyVerification = EventVerification.FAILED;
                return;
            }
        }

        this.consistencyVerification = EventVerification.SUCCESS;
    }


    public void verifyMembershipProof(byte[] rootHash){
        if(this.membershipProof == null || this.membershipProof.isEmpty()){
            return;
        }
        byte[] nodeHash = Hash.decode(this.hash);
        MembershipProof proof = SearchEvent.decodeMembershipProof(this.membershipProof);
        this.membershipVerification = SearchEvent.verifyMembershipProof(rootHash, nodeHash, proof)? EventVerification.SUCCESS : EventVerification.FAILED;
    }

    static private boolean verifyMembershipProof(byte[] rootHash, byte[] nodeHash, MembershipProof proof){        
        for(MembershipProofItem item: proof){
            try{
                nodeHash = item.getSide().equals("left")? Hash.hash(item.getHash(), nodeHash) : Hash.hash(nodeHash, item.getHash());
            } catch(IOException e){
                return false;
            }
        }
        return Arrays.equals(nodeHash, rootHash);
    }

    static private MembershipProof decodeMembershipProof(String memProof){
        MembershipProof proof = new MembershipProof();

        for(String item: memProof.split(",")){
            String[] parts = item.split(":");
            String side = parts[0].equals("l")?  "left" : "right";
            MembershipProofItem proofItem = new MembershipProofItem(side, Hash.decode(parts[1]));
            proof.add(proofItem);
        }

        return proof;
    }

}
