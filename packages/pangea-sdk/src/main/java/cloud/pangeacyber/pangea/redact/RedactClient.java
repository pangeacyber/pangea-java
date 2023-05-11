package cloud.pangeacyber.pangea.redact;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.redact.requests.RedactStructuredRequest;
import cloud.pangeacyber.pangea.redact.requests.RedactTextRequest;

public class RedactClient extends Client {

	public static String serviceName = "redact";

	public RedactClient(Config config) {
		super(config, serviceName);
	}

	private RedactTextResponse redactPost(RedactTextRequest request) throws PangeaException, PangeaAPIException {
		RedactTextResponse resp = doPost("/v1/redact", request, RedactTextResponse.class);
		return resp;
	}

	private RedactStructuredResponse structuredPost(RedactStructuredRequest request)
		throws PangeaException, PangeaAPIException {
		RedactStructuredResponse resp = doPost("/v1/redact_structured", request, RedactStructuredResponse.class);
		return resp;
	}

	/**
	 * Redact
	 * @pangea.description Redact sensitive information from provided text.
	 * @deprecated use redactText(RedactTextRequest request) instead.
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
		return redactPost(new RedactTextRequest.RedactTextRequestBuilder(text).build());
	}

	/**
	 * Redact - text, debug
	 * @pangea.description Redact sensitive information from provided text.
	 * @deprecated use redactText(RedactTextRequest request) instead.
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
		return redactPost(new RedactTextRequest.RedactTextRequestBuilder(text).setDebug(debug).build());
	}

	/**
	 * Redact - text, debug, rules
	 * @pangea.description Redact sensitive information from provided text.
	 * @deprecated use redactText(RedactTextRequest request) instead.
	 * @param text The text data to redact.
	 * @param debug Setting this value to true will provide a detailed analysis of the redacted data and the rules that caused redaction.
	 * @param rules An array of redact rule short names.
	 * @return RedactTextResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * RedactTextResponse response = client.redactText("Jenny Jenny... 415-867-5309", true, new String[] {"PHONE_NUMBER"});
	 * }
	 */
	public RedactTextResponse redactText(String text, Boolean debug, String[] rules)
		throws PangeaException, PangeaAPIException {
		return redactPost(new RedactTextRequest.RedactTextRequestBuilder(text).setDebug(debug).setRules(rules).build());
	}

	/**
	 * Redact
	 * @pangea.description Redact sensitive information from provided text.
	 * @pangea.operationId redact_post_v1_redact
	 * @param request redact request with text and optional parameters
	 * @return RedactTextResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * 		RedactTextResponse response = client.redactText(
	 *			new RedactTextRequest.RedactTextRequestBuilder("Jenny Jenny... 415-867-5309").build()
	 *		);
	 * }
	 */
	public RedactTextResponse redactText(RedactTextRequest request) throws PangeaException, PangeaAPIException {
		return redactPost(request);
	}

	/**
	 * Redact structured
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @deprecated use redactStructured(RedactStructuredRequest request) instead.
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
		return structuredPost(new RedactStructuredRequest.RedactStructuredRequestBuilder(data).build());
	}

	/**
	 * Redact structured - rules
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @deprecated use redactStructured(RedactStructuredRequest request) instead.
	 * @param data Structured data to redact
	 * @param rules An array of redact rule short names
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
	 * RedactStructuredResponse response = client.redactStructured(data, new String[] {"PHONE_NUMBER"});
	 * }
	 */
	public RedactStructuredResponse redactStructured(Object data, String[] rules)
		throws PangeaException, PangeaAPIException {
		return structuredPost(new RedactStructuredRequest.RedactStructuredRequestBuilder(data).setRules(rules).build());
	}

	/**
	 * Redact structured - data, format
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @deprecated use redactStructured(RedactStructuredRequest request) instead.
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
		return structuredPost(
			new RedactStructuredRequest.RedactStructuredRequestBuilder(data).setFormat(format).build()
		);
	}

	/**
	 * Redact structured - data, debug
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @deprecated use redactStructured(RedactStructuredRequest request) instead.
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
		return structuredPost(new RedactStructuredRequest.RedactStructuredRequestBuilder(data).setDebug(debug).build());
	}

	/**
	 * Redact structured - data, format, debug
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @deprecated use redactStructured(RedactStructuredRequest request) instead.
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
		return structuredPost(
			new RedactStructuredRequest.RedactStructuredRequestBuilder(data).setFormat(format).setDebug(debug).build()
		);
	}

	/**
	 * Redact structured - data, debug, jsonp
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @deprecated use redactStructured(RedactStructuredRequest request) instead.
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
		return structuredPost(
			new RedactStructuredRequest.RedactStructuredRequestBuilder(data).setDebug(debug).setJsonp(jsonp).build()
		);
	}

	/**
	 * Redact structured - data, debug, jsonp, rules
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @deprecated use redactStructured(RedactStructuredRequest request) instead.
	 * @param data Structured data to redact
	 * @param debug Setting this value to true will provide a detailed analysis of the redacted data and the rules that caused redaction.
	 * @param jsonp JSON path(s) used to identify the specific JSON fields to redact in the structured data. Note: data parameter must be in JSON format.
	 * @param rules An array of redact rule short names
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
	 * RedactStructuredResponse response = client.redactStructured(data, true, new String[] {"Phone"}, new String[] {"PHONE_NUMBER"});
	 * }
	 */
	public RedactStructuredResponse redactStructured(Object data, boolean debug, String[] jsonp, String[] rules)
		throws PangeaException, PangeaAPIException {
		return structuredPost(
			new RedactStructuredRequest.RedactStructuredRequestBuilder(data)
				.setDebug(debug)
				.setJsonp(jsonp)
				.setRules(rules)
				.build()
		);
	}

	/**
	 * Redact structured
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
	 * @pangea.operationId redact_post_v1_redact_structured
	 * @param request redact structured request with object data and optional parameters
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
	 *	RedactStructuredResponse response = client.redactStructured(
	 *		new RedactStructuredRequest.RedactStructuredRequestBuilder(data)
	 *			.setDebug(true)
	 *			.setJsonp(new String[] { "Phone", "Name" })
	 *			.setRules(new String[] { "PHONE_NUMBER" })
	 *			.build()
	 *	);
	 * }
	 */
	public RedactStructuredResponse redactStructured(RedactStructuredRequest request)
		throws PangeaException, PangeaAPIException {
		return structuredPost(request);
	}
}
