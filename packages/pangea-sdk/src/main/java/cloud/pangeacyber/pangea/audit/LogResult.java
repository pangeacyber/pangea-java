package cloud.pangeacyber.pangea.audit;

import cloud.pangeacyber.pangea.audit.models.EventEnvelope;
import cloud.pangeacyber.pangea.audit.models.EventVerification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.TreeMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogResult {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("envelope")
	TreeMap<String, Object> rawEnvelope;

	@JsonIgnore
	EventEnvelope eventEnvelope = null;

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

	protected TreeMap<String, Object> getRawEnvelope() {
		return rawEnvelope;
	}

	public EventEnvelope getEventEnvelope() {
		return eventEnvelope;
	}

	protected void setEventEnvelope(EventEnvelope eventEnvelope) {
		this.eventEnvelope = eventEnvelope;
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
		if (this.eventEnvelope != null) {
			this.signatureVerification = this.eventEnvelope.verifySignature();
		} else {
			this.signatureVerification = EventVerification.NOT_VERIFIED;
		}
	}
}
