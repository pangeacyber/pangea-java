package cloud.pangeacyber.pangea.redact;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.redact.requests.RedactStructuredRequest;
import cloud.pangeacyber.pangea.redact.requests.RedactTextRequest;
import cloud.pangeacyber.pangea.redact.responses.RedactStructuredResponse;
import cloud.pangeacyber.pangea.redact.responses.RedactTextResponse;

public class RedactClient extends BaseClient {
	public RedactClient(Builder builder) {
		super(builder);
	}

	public static class Builder extends BaseClient.Builder<Builder> {
		public Builder(Config config) {
			super(config, "redact");
		}

		public RedactClient build() {
			return new RedactClient(this);
		}
	}

	/**
	 * Redact
	 * @pangea.description Redact sensitive information from provided text.
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
		return post("/v1/redact", request, RedactTextResponse.class);
	}

	/**
	 * Redact structured
	 * @pangea.description Redact sensitive information from structured data (e.g., JSON).
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
		return post("/v1/redact_structured", request, RedactStructuredResponse.class);
	}
}
