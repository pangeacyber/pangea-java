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
        if(this.signature.isEmpty() && this.publicKey.isEmpty()){
            return EventVerification.NOT_VERIFIED;
        }
        ObjectMapper mapper = new ObjectMapper();
        String canonicalJson;
        try{
            canonicalJson = mapper.writeValueAsString(this.event);
        } catch(JsonProcessingException e){
            return EventVerification.FAILED;
        }
        Verifier verifier = new Verifier();
        return verifier.verify(this.publicKey, this.signature, canonicalJson);
    }
}