package cloud.pangeacyber.pangea.ai_guard;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.ai_guard.requests.TextGuardRequest;
import cloud.pangeacyber.pangea.ai_guard.responses.TextGuardResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.core.type.TypeReference;

/** AI Guard API client. */
public class AIGuardClient extends BaseClient {

	public static final String serviceName = "ai-guard";

	/** Creates a new AI Guard API client. */
	public AIGuardClient(Builder builder) {
		super(builder, serviceName);
	}

	/** Builder of AI Guard API clients. */
	public static class Builder extends BaseClient.Builder<Builder> {

		/** Creates a new builder. */
		public Builder(Config config) {
			super(config);
		}

		/** Builds a new AI Guard API client. */
		public AIGuardClient build() {
			return new AIGuardClient(this);
		}
	}

	/**
	 * Text guard
	 * @pangea.description Guard text.
	 * @pangea.operationId ai_guard_post_v1_text_guard
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * final var response = client.guardText(TextGuardRequest.builder().text("hello world").build());
	 * }
	 */
	public <TMessages> TextGuardResponse<TMessages> guardText(final TextGuardRequest<TMessages> request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/text/guard", request, new TypeReference<TextGuardResponse<TMessages>>() {});
	}
}
