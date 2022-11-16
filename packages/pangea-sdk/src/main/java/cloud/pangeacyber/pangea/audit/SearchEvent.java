package cloud.pangeacyber.pangea.audit;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.audit.arweave.PublishedRoot;
import cloud.pangeacyber.pangea.audit.utils.ConsistencyProof;
import cloud.pangeacyber.pangea.audit.utils.Verification;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchEvent {
    @JsonProperty("envelope")
    Object rawEnvelope;

    EventEnvelope eventEnvelope;

    @JsonProperty("hash")
    String hash;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("leaf_index")
    Integer leafIndex;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("membership_proof")
    String membershipProof;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("published")
    Boolean published;

    @JsonIgnore
    EventVerification membershipVerification = EventVerification.NOT_VERIFIED;

    @JsonIgnore
    EventVerification consistencyVerification = EventVerification.NOT_VERIFIED;

    @JsonIgnore
    EventVerification signatureVerification = EventVerification.NOT_VERIFIED;

    public Object getRawEnvelope() {
        return rawEnvelope;
    }

    public EventEnvelope getEventEnvelope() {
        return eventEnvelope;
    }

    public void setEventEnvelope(EventEnvelope eventEnvelope) {
        this.eventEnvelope = eventEnvelope;
    }

    public String getHash() {
        return hash;
    }

    public Integer getLeafIndex() {
        return leafIndex;
    }

    public String getMembershipProof() {
        return membershipProof;
    }

    public boolean isPublished() {
        return published == true;
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

    public void verifySignature() {
        if(this.eventEnvelope != null){
            this.signatureVerification = this.eventEnvelope.verifySignature();
        } else {
            this.signatureVerification = EventVerification.NOT_VERIFIED;
        }
    }

    public void verifyConsistency(Map<Integer, PublishedRoot> publishedRoots){
        // This should never happen.
        if(!isPublished() || leafIndex == null){
            this.consistencyVerification = EventVerification.NOT_VERIFIED;
            return;
        }

        if(leafIndex == 0){
            this.consistencyVerification = EventVerification.SUCCESS;
            return;
        }

        if(leafIndex < 0){
            this.consistencyVerification = EventVerification.FAILED;
            return;
        }

        if(!publishedRoots.containsKey(leafIndex) || !publishedRoots.containsKey(leafIndex+1)){
            this.consistencyVerification = EventVerification.NOT_VERIFIED;
            return;
        }

        PublishedRoot currRoot = publishedRoots.get(leafIndex+1);
        PublishedRoot prevRoot = publishedRoots.get(leafIndex);
        ConsistencyProof proof = Verification.decodeConsistencyProof(currRoot.getConsistencyProof());
        this.consistencyVerification = Verification.verifyConsistencyProof(currRoot.getRootHash(), prevRoot.getRootHash(), proof);
    }

    public void verifyMembershipProof(String rootHashEnc){
        if(this.membershipProof == null){
            this.membershipVerification = EventVerification.NOT_VERIFIED;
            return;
        }
        this.membershipVerification = Verification.verifyMembershipProof(rootHashEnc, this.hash, this.membershipProof);
    }

}
