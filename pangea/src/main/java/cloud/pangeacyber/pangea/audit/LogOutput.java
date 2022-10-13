package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class LogOutput {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("envelope")
    EventEnvelope eventEnvelope;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("hash")
    String hash;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("canonical_envelope_base64")
    String canonicalEnvelopeBase64;

    public EventEnvelope getEventEnvelope() {
        return eventEnvelope;
    }

    public String getHash() {
        return hash;
    }

    public String getCanonicalEnvelopeBase64() {
        return canonicalEnvelopeBase64;
    }
}
