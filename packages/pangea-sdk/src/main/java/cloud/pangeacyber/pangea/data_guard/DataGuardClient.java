package cloud.pangeacyber.pangea.data_guard;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.data_guard.requests.FileGuardRequest;
import cloud.pangeacyber.pangea.data_guard.requests.TextGuardRequest;
import cloud.pangeacyber.pangea.data_guard.responses.TextGuardResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.core.type.TypeReference;

/** Data Guard API client. */
public class DataGuardClient extends BaseClient {

	public static final String serviceName = "data-guard";

	/** Creates a new Data Guard API client. */
	public DataGuardClient(Builder builder) {
		super(builder, serviceName);
	}

	/** Builder of Data Guard API clients. */
	public static class Builder extends BaseClient.Builder<Builder> {

		/** Creates a new builder. */
		public Builder(Config config) {
			super(config);
		}

		/** Builds a new Data Guard API client. */
		public DataGuardClient build() {
			return new DataGuardClient(this);
		}
	}

	/**
	 * Text guard (Beta)
	 * @pangea.description Guard text.
	 * @pangea.operationId data_guard_post_v1_text_guard
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * final var response = client.guardText(TextGuardRequest.builder().text("hello world").build());
	 * }
	 */
	public TextGuardResponse guardText(final TextGuardRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/text/guard", request, TextGuardResponse.class);
	}

	/**
	 * File guard (Beta)
	 * @pangea.description Guard a file URL.
	 * @pangea.operationId data_guard_post_v1_file_guard
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * final var response = client.guardFile(
	 * 	FileGuardRequest.builder().fileUrl("https://pangea.cloud/robots.txt").build()
	 * );
	 * }
	 */
	public Response<Void> guardFile(final FileGuardRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/file/guard", request, new TypeReference<Response<Void>>() {});
	}
}
