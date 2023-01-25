package cloud.pangeacyber.pangea.redact;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class TextRequest {

	@JsonProperty("text")
	String text;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("debug")
	Boolean debug;

	public TextRequest(String text, Boolean debug) {
		this.text = text;
		this.debug = debug;
	}
}

final class StructuredRequest {

	@JsonProperty("data")
	Object data;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("jsonp")
	String[] jsonp;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("format")
	String format;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("debug")
	Boolean debug;

	StructuredRequest(Object data, String[] jsonp, String format, Boolean debug) {
		this.data = data;
		this.jsonp = jsonp;
		this.format = format;
		this.debug = debug;
	}
}

public class RedactClient extends Client {

	public static String serviceName = "redact";

	public RedactClient(Config config) {
		super(config, serviceName);
	}

	private RedactTextResponse redactPost(String text, Boolean debug) throws PangeaException, PangeaAPIException {
		TextRequest request = new TextRequest(text, debug);
		RedactTextResponse resp = doPost("/v1/redact", request, RedactTextResponse.class);
		return resp;
	}

	private RedactStructuredResponse structuredPost(Object data, String format, Boolean debug, String[] jsonp)
		throws PangeaException, PangeaAPIException {
		StructuredRequest request = new StructuredRequest(data, jsonp, null, debug);
		RedactStructuredResponse resp = doPost("/v1/redact_structured", request, RedactStructuredResponse.class);
		return resp;
	}

	/**
	 * Redact
	 * @pangea.description Redact sensitive information from provided text.
	 * @param text The text data to redact.
	 * @return RedactTextResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * RedactTextResponse response = client.redactText("Jenny Jenny... 415-867-5309");
	 * }
	 */
	public RedactTextResponse redactText(String text) throws PangeaException, PangeaAPIException {
		return redactPost(text, null);
	}

	/**
	 * Redact - text, debug
	 * @pangea.description Redact sensitive information from provided text.
	 * @param text The text data to redact.
	 * @param debug Setting this value to true will provide a detailed analysis of the redacted data and the rules that caused redaction.
	 * @return RedactTextResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * RedactTextResponse response = client.redactText("Jenny Jenny... 415-867-5309", true);
	 * }
	 */
	public RedactTextResponse redactText(String text, boolean debug) throws PangeaException, PangeaAPIException {
		return redactPost(text, debug);
	}

	/**
	 * Redact structured
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @param data Structured data to redact
	 * @return RedactStructuredResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * Map<String, Object> data = new LinkedHashMap<String, Object>();
	 *
	 * data.put("Name", "Jenny Jenny");
	 * data.put("Phone", "This is its number: 415-867-5309");
	 *
	 * RedactStructuredResponse response = client.redactStructured(data);
	 * }
	 */
	public RedactStructuredResponse redactStructured(Object data) throws PangeaException, PangeaAPIException {
		return structuredPost(data, null, null, null);
	}

	/**
	 * Redact structured - data, format
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @param data Structured data to redact
	 * @param format format of data. Support "json"
	 * @return RedactStructuredResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * Map<String, Object> data = new LinkedHashMap<String, Object>();
	 *
	 * data.put("Name", "Jenny Jenny");
	 * data.put("Phone", "This is its number: 415-867-5309");
	 *
	 * RedactStructuredResponse response = client.redactStructured(data, "json");
	 * }
	 */
	public RedactStructuredResponse redactStructured(Object data, String format)
		throws PangeaException, PangeaAPIException {
		return structuredPost(data, format, null, null);
	}

	/**
	 * Redact structured - data, debug
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @param data Structured data to redact
	 * @param debug Setting this value to true will provide a detailed analysis of the redacted data and the rules that caused redaction.
	 * @return RedactStructuredResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * Map<String, Object> data = new LinkedHashMap<String, Object>();
	 *
	 * data.put("Name", "Jenny Jenny");
	 * data.put("Phone", "This is its number: 415-867-5309");
	 *
	 * RedactStructuredResponse response = client.redactStructured(data, true);
	 * }
	 */
	public RedactStructuredResponse redactStructured(Object data, boolean debug)
		throws PangeaException, PangeaAPIException {
		return structuredPost(data, null, debug, null);
	}

	/**
	 * Redact structured - data, format, debug
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @param data Structured data to redact
	 * @param format format of data. Support "json"
	 * @param debug Setting this value to true will provide a detailed analysis of the redacted data and the rules that caused redaction.
	 * @return RedactStructuredResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * Map<String, Object> data = new LinkedHashMap<String, Object>();
	 *
	 * data.put("Name", "Jenny Jenny");
	 * data.put("Phone", "This is its number: 415-867-5309");
	 *
	 * RedactStructuredResponse response = client.redactStructured(data, "json", true);
	 * }
	 */
	public RedactStructuredResponse redactStructured(Object data, String format, boolean debug)
		throws PangeaException, PangeaAPIException {
		return structuredPost(data, format, debug, null);
	}

	/**
	 * Redact structured - data, debug, jsonp
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @param data Structured data to redact
	 * @param debug Setting this value to true will provide a detailed analysis of the redacted data and the rules that caused redaction.
	 * @param jsonp JSON path(s) used to identify the specific JSON fields to redact in the structured data. Note: data parameter must be in JSON format.
	 * @return RedactStructuredResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * Map<String, Object> data = new LinkedHashMap<String, Object>();
	 *
	 * data.put("Name", "Jenny Jenny");
	 * data.put("Phone", "This is its number: 415-867-5309");
	 *
	 * RedactStructuredResponse response = client.redactStructured(data, true, new String[] {"Phone"});
	 * }
	 */
	public RedactStructuredResponse redactStructured(Object data, boolean debug, String[] jsonp)
		throws PangeaException, PangeaAPIException {
		return structuredPost(data, null, debug, jsonp);
	}
}
