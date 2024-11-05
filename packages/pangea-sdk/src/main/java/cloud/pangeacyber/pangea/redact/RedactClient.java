package cloud.pangeacyber.pangea.redact;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.redact.requests.RedactStructuredRequest;
import cloud.pangeacyber.pangea.redact.requests.RedactTextRequest;
import cloud.pangeacyber.pangea.redact.requests.UnredactRequest;
import cloud.pangeacyber.pangea.redact.responses.RedactStructuredResponse;
import cloud.pangeacyber.pangea.redact.responses.RedactTextResponse;
import cloud.pangeacyber.pangea.redact.responses.UnredactResponse;
import com.fasterxml.jackson.core.type.TypeReference;

public class RedactClient extends BaseClient {

	public static final String serviceName = "redact";

	public RedactClient(Builder builder) {
		super(builder, serviceName);
		setConfigID(builder.configID);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		String configID = null;

		public Builder(Config config) {
			super(config);
		}

		public RedactClient build() {
			return new RedactClient(this);
		}

		public Builder withConfigID(String configID) {
			this.configID = configID;
			return this;
		}
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
	 * final var response = client.redactText(
	 * 	new RedactTextRequest.RedactTextRequestBuilder("Jenny Jenny... 415-867-5309").build()
	 * );
	 * }
	 */
	public RedactTextResponse redactText(RedactTextRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/redact", request, RedactTextResponse.class);
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
	 * final var data = new LinkedHashMap<String, Object>();
	 *
	 * data.put("Name", "Jenny Jenny");
	 * data.put("Phone", "This is its number: 415-867-5309");
	 *
	 * final var response = client.redactStructured(
	 * 	new RedactStructuredRequest.RedactStructuredRequestBuilder(data)
	 * 		.setDebug(true)
	 * 		.setJsonp(new String[] { "Phone", "Name" })
	 * 		.setRules(new String[] { "PHONE_NUMBER" })
	 * 		.build()
	 * );
	 * }
	 */
	public RedactStructuredResponse redactStructured(RedactStructuredRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/redact_structured", request, RedactStructuredResponse.class);
	}

	/**
	 * Unredact
	 * @pangea.description Decrypt or unredact fpe redactions
	 * @pangea.operationId redact_post_v1_unredact
	 * @param request Unredact request with data and fpe context
	 * @return UnredactResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 */
	public <T> UnredactResponse<T> unredact(UnredactRequest<T> request) throws PangeaException, PangeaAPIException {
		return post("/v1/unredact", request, new TypeReference<UnredactResponse<T>>() {});
	}
}
