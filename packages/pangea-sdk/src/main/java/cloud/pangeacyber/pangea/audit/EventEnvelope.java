package cloud.pangeacyber.pangea.audit;

import cloud.pangeacyber.pangea.audit.utils.Hash;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.VerificationFailed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventEnvelope {

	@JsonIgnore
	IEvent ievent = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("signature")
	String signature;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("public_key")
	String publicKey;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("received_at")
	String receivedAt;

	protected EventEnvelope(IEvent ievent, String signature, String publicKey, String receivedAt) {
		this.ievent = ievent;
		this.signature = signature;
		this.publicKey = publicKey;
		this.receivedAt = receivedAt;
	}

	public IEvent getEvent() {
		return ievent;
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

	public EventVerification verifySignature() {
		// If does not have signature information, it's not verified
		if (this.signature == null && this.publicKey == null) {
			return EventVerification.NOT_VERIFIED;
		}

		// But if one field is missing, it's verification failed
		if (this.signature == null || this.publicKey == null) {
			return EventVerification.FAILED;
		}

		String pubKey = this.getPublicKeyValue();
		if (pubKey == null) {
			return EventVerification.FAILED;
		}

		String canonicalJson = "";
		try {
			canonicalJson = IEvent.canonicalize(this.ievent);
		} catch (JsonProcessingException e) {
			return EventVerification.FAILED;
		}
		Verifier verifier = new Verifier();
		return verifier.verify(pubKey, this.signature, canonicalJson);
	}

	private String getPublicKeyValue() {
		if (this.publicKey == null) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> pkJSON;
		try {
			// This to parse publicKey field as JSON
			pkJSON = mapper.readValue(this.publicKey, Map.class);
			return (String) pkJSON.get("key");
		} catch (JacksonException e) {
			// If it's not JSON format just return raw publicKey
			return this.publicKey;
		}
	}

	public static String canonicalize(Map<String, Object> rawEnvelope) throws PangeaException {
		ObjectMapper mapper = JsonMapper
			.builder()
			.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
			.build();
		String canon;

		if (rawEnvelope.containsKey("event")) {
			// TreeMap to store sorted values of HashMap
			TreeMap<String, Object> sortedEvent = new TreeMap<>();
			sortedEvent.putAll((LinkedHashMap<String, Object>) rawEnvelope.get("event"));
			rawEnvelope.put("event", sortedEvent);
		}
		// TreeMap to store sorted values of HashMap
		TreeMap<String, Object> sortedEnvelope = new TreeMap<>();
		sortedEnvelope.putAll(rawEnvelope);

		try {
			canon = mapper.writeValueAsString(sortedEnvelope);
		} catch (JsonProcessingException e) {
			throw new PangeaException("Failed to canonicalize event envelope", e);
		}

		return canon;
	}

	public static <EventType extends IEvent> EventEnvelope fromRaw(Map<String, Object> raw, Class<EventType> eventType)
		throws PangeaException {
		if (raw == null) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		EventEnvelopeDeserializer eventEnvelopeDeserializer;
		try {
			eventEnvelopeDeserializer =
				mapper.readValue(mapper.writeValueAsString(raw), EventEnvelopeDeserializer.class);
		} catch (JsonProcessingException e) {
			System.out.println(e.toString());
			throw new PangeaException("Failed to process event envelope", e);
		}

		IEvent event = IEvent.fromRaw(eventEnvelopeDeserializer.getRawEvent(), eventType);
		return new EventEnvelope(
			event,
			eventEnvelopeDeserializer.getSignature(),
			eventEnvelopeDeserializer.getPublicKey(),
			eventEnvelopeDeserializer.getReceivedAt()
		);
	}

	public static void verifyHash(Map<String, Object> rawEnvelope, String hash) throws VerificationFailed {
		if (rawEnvelope == null || hash == null || hash.isEmpty()) {
			return;
		}

		String canonicalJson;
		try {
			canonicalJson = EventEnvelope.canonicalize(rawEnvelope);
		} catch (PangeaException e) {
			throw new VerificationFailed(
				"Failed to canonicalize envelope in hash verification. Event hash: " + hash,
				e,
				hash
			);
		}

		String calcHash = Hash.hash(canonicalJson);
		if (!calcHash.equals(hash)) {
			throw new VerificationFailed(
				"Failed hash verification. Calculated and received hash are not equals. Event hash: " + hash,
				null,
				hash
			);
		}
	}
}

final class EventEnvelopeDeserializer {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("event")
	TreeMap<String, Object> rawEvent;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("signature")
	String signature;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("public_key")
	String publicKey;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("received_at")
	String receivedAt;

	public String getSignature() {
		return signature;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getReceivedAt() {
		return receivedAt;
	}

	protected Object getRawEvent() {
		return rawEvent;
	}
}
