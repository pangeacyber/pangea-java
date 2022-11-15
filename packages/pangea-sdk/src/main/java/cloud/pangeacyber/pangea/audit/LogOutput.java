package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class LogOutput {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("envelope")
    EventEnvelope eventEnvelope;

    @JsonProperty("hash")
    String hash;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("unpublished_root")
    String unpublishedRoot;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("membership_proof")
    String membershipProof;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("consistency_proof")
    String[] consistencyProof;

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

    public String getUnpublishedRoot() {
        return unpublishedRoot;
    }

    public String getMembershipProof() {
        return membershipProof;
    }

    public String[] getConsistencyProof() {
        return consistencyProof;
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
}