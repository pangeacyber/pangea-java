package cloud.pangeacyber.pangea.ai_guard;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.ai_guard.models.ServiceConfig;
import cloud.pangeacyber.pangea.ai_guard.requests.CreateServiceConfigParams;
import cloud.pangeacyber.pangea.ai_guard.requests.DeleteServiceConfigParams;
import cloud.pangeacyber.pangea.ai_guard.requests.GetServiceConfigParams;
import cloud.pangeacyber.pangea.ai_guard.requests.GuardRequest;
import cloud.pangeacyber.pangea.ai_guard.requests.ListServiceConfigsParams;
import cloud.pangeacyber.pangea.ai_guard.requests.TextGuardRequest;
import cloud.pangeacyber.pangea.ai_guard.requests.UpdateServiceConfigParams;
import cloud.pangeacyber.pangea.ai_guard.responses.TextGuardResponse;
import cloud.pangeacyber.pangea.ai_guard.results.GuardResult;
import cloud.pangeacyber.pangea.ai_guard.results.ListServiceConfigsResult;
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
		if (request.getText() == null && request.getMessages() == null) {
			throw new IllegalArgumentException("One of `text` or `messages` must be provided");
		}

		return post("/v1/text/guard", request, new TypeReference<TextGuardResponse<TMessages>>() {});
	}

	/**
	 * Guard LLM input and output
	 * @pangea.description Analyze and redact content to avoid manipulation of
	 *   the model, addition of malicious content, and other undesirable data transfers.
	 * @pangea.operationId ai_guard_post_v1beta_guard
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 */
	public Response<GuardResult> guard(final GuardRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1beta/guard", request, new TypeReference<Response<GuardResult>>() {});
	}

	/**
	 * @pangea.operationId ai_guard_post_v1beta_config
	 * @param body Request body.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 */
	public Response<ServiceConfig> getServiceConfig(final GetServiceConfigParams body)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/config", body, new TypeReference<Response<ServiceConfig>>() {});
	}

	/**
	 * @pangea.operationId ai_guard_post_v1beta_config_create
	 * @param body Request body.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 */
	public Response<ServiceConfig> createServiceConfig(final CreateServiceConfigParams body)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/config/create", body, new TypeReference<Response<ServiceConfig>>() {});
	}

	/**
	 * @pangea.operationId ai_guard_post_v1beta_config_update
	 * @param body Request body.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 */
	public Response<ServiceConfig> updateServiceConfig(final UpdateServiceConfigParams body)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/config/update", body, new TypeReference<Response<ServiceConfig>>() {});
	}

	/**
	 * @pangea.operationId ai_guard_post_v1beta_config_delete
	 * @param body Request body.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 */
	public Response<ServiceConfig> deleteServiceConfig(final DeleteServiceConfigParams body)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/config/delete", body, new TypeReference<Response<ServiceConfig>>() {});
	}

	/**
	 * @pangea.operationId ai_guard_post_v1beta_config_list
	 * @param body Request body.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 */
	public Response<ListServiceConfigsResult> listServiceConfigs(final ListServiceConfigsParams body)
		throws PangeaException, PangeaAPIException {
		return post("/v1beta/config/list", body, new TypeReference<Response<ListServiceConfigsResult>>() {});
	}
}
