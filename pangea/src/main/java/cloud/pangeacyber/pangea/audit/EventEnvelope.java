package cloud.pangeacyber.pangea.audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
