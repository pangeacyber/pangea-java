package cloud.pangeacyber.pangea.audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cloud.pangeacyber.pangea.audit.utils.Hash;
import cloud.pangeacyber.pangea.exceptions.VerificationFailed;

public class EventEnvelope {
    @JsonProperty("event")
    Event event;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("signature")
    String signature;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("public_key")
    String publicKey;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("received_at")
    String receivedAt;

    public Event getEvent() {
        return event;
    }

    public String getSignature() {
        return signature;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getReceivedAt() {
        return receivedAt;
    }

    public EventVerification verifySignature(){
        // If does not have signature information, it's not verified
        if(this.signature == null && this.publicKey == null){
            return EventVerification.NOT_VERIFIED;
        }

        // But if one field is missing, it's verification failed
        if(this.signature == null || this.publicKey == null){
            return EventVerification.FAILED;
        }

        String canonicalJson;
        try{
            canonicalJson = Event.canonicalize(this.event);
        } catch(JsonProcessingException e){
            return EventVerification.FAILED;
        }
        Verifier verifier = new Verifier();
        return verifier.verify(this.publicKey, this.signature, canonicalJson);
    }

    static public String canonicalize(EventEnvelope envelope) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        return mapper.writeValueAsString(envelope);
    }

    public static void verifyHash(EventEnvelope envelope, String hash) throws VerificationFailed {
        if(envelope == null || hash == null || hash.isEmpty()){
            return;
        }

        String canonicalJson;
        try{
            canonicalJson = EventEnvelope.canonicalize(envelope);
        } catch(JsonProcessingException e){
            throw new VerificationFailed("Failed to canonicalize envelope in hash verification. Event hash: " + hash, e, hash);
        }

        String calcHash = Hash.hash(canonicalJson);
        if(!calcHash.equals(hash)){
            throw new VerificationFailed("Failed hash verification. Calculated and received hash are not equals. Event hash: " + hash, null, hash);
        }
    }
}
