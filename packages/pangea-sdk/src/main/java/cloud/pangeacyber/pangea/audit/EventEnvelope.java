package cloud.pangeacyber.pangea.audit;

import cloud.pangeacyber.pangea.audit.utils.Hash;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.VerificationFailed;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@JsonIgnoreProperties(ignoreUnknown = true)
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

		String canonicalJson;
		try {
			canonicalJson = Event.canonicalize(this.event);
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
		Map<Object, Object> pkJSON;
		try {
			// This to parse publicKey field as JSON
			pkJSON = mapper.readValue(this.publicKey, (Map.class));
			return (String) pkJSON.get("key");
		} catch (JacksonException e) {
			// If it's not JSON format just return raw publicKey
			return this.publicKey;
		}
	}

	public static String canonicalize(Object rawEnvelope) throws PangeaException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		LinkedHashMap<String, Object> mapEnvelope = (LinkedHashMap<String, Object>) rawEnvelope;
		String canon;

		if (mapEnvelope.containsKey("event")) {
			// TreeMap to store sorted values of HashMap
			TreeMap<String, Object> sortedEvent = new TreeMap<>();
			sortedEvent.putAll((LinkedHashMap<String, Object>) mapEnvelope.get("event"));
			mapEnvelope.put("event", sortedEvent);
		}
		// TreeMap to store sorted values of HashMap
		TreeMap<String, Object> sortedEnvelope = new TreeMap<>();
		sortedEnvelope.putAll(mapEnvelope);

		try {
			canon = mapper.writeValueAsString(sortedEnvelope);
		} catch (JsonProcessingException e) {
			throw new PangeaException("Failed to canonicalize event envelope", e);
		}

		return canon;
	}

	public static EventEnvelope fromRaw(Object raw) throws PangeaException {
		if (raw == null) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		EventEnvelope eventEnvelope;
		try {
			eventEnvelope = mapper.readValue(mapper.writeValueAsString(raw), EventEnvelope.class);
		} catch (JsonProcessingException e) {
			throw new PangeaException("Failed to process event envelope", e);
		}

		return eventEnvelope;
	}

	public static void verifyHash(Object rawEnvelope, String hash) throws VerificationFailed {
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
