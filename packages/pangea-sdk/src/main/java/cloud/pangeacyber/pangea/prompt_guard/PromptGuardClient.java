package cloud.pangeacyber.pangea.prompt_guard;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.prompt_guard.requests.GuardRequest;
import cloud.pangeacyber.pangea.prompt_guard.responses.GuardResponse;

/** Prompt Guard API client. */
public class PromptGuardClient extends BaseClient {

	public static final String serviceName = "prompt-guard";

	/** Creates a new Prompt Guard API client. */
	public PromptGuardClient(Builder builder) {
		super(builder, serviceName);
	}

	/** Builder of Prompt Guard API clients. */
	public static class Builder extends BaseClient.Builder<Builder> {

		/** Creates a new builder. */
		public Builder(Config config) {
			super(config);
		}

		/** Builds a new Prompt Guard API client. */
		public PromptGuardClient build() {
			return new PromptGuardClient(this);
		}
	}

	/**
	 * Guard (Beta)
	 * @pangea.description Guard messages.
	 * @pangea.operationId prompt_guard_post_v1beta_guard
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * final var response = client.guard(GuardRequest.builder().messages(List.of(
	 * 	new Message("user", "how are you?")
	 * )).build());
	 * }
	 */
	public GuardResponse guard(final GuardRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/guard", request, GuardResponse.class);
	}
}
