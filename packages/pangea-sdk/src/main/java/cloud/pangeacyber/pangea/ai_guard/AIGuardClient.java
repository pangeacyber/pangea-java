package cloud.pangeacyber.pangea.ai_guard;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.ai_guard.models.Message;
import cloud.pangeacyber.pangea.ai_guard.requests.GuardRequest;
import cloud.pangeacyber.pangea.ai_guard.requests.TextGuardRequest;
import cloud.pangeacyber.pangea.ai_guard.results.GuardResult;
import cloud.pangeacyber.pangea.ai_guard.results.TextGuardResult;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Value;

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

	@AllArgsConstructor
	@Value
	private static class RelevantContent {

		List<Message> messages;
		List<Integer> indices;
	}

	private RelevantContent getRelevantContent(final List<Message> messages) {
		if (messages.isEmpty()) {
			return new RelevantContent(new ArrayList<>(), new ArrayList<>());
		}

		final var systemMessages = messages
			.stream()
			.filter(msg -> "system".equals(msg.getRole()))
			.collect(Collectors.toList());

		final var systemIndices = IntStream
			.range(0, messages.size())
			.filter(i -> "system".equals(messages.get(i).getRole()))
			.boxed()
			.collect(Collectors.toList());

		if ("assistant".equals(messages.get(messages.size() - 1).getRole())) {
			final var relevantMessages = new ArrayList<>(systemMessages);
			relevantMessages.add(messages.get(messages.size() - 1));
			final var relevantIndices = new ArrayList<>(systemIndices);
			relevantIndices.add(messages.size() - 1);
			return new RelevantContent(relevantMessages, relevantIndices);
		}

		var lastAssistantIndex = -1;
		for (var i = messages.size() - 1; i >= 0; i--) {
			if ("assistant".equals(messages.get(i).getRole())) {
				lastAssistantIndex = i;
				break;
			}
		}

		final var relevantMessages = new ArrayList<Message>();
		final var indices = new ArrayList<Integer>();
		for (var i = 0; i < messages.size(); i++) {
			final var msg = messages.get(i);
			if ("system".equals(msg.getRole()) || i > lastAssistantIndex) {
				relevantMessages.add(msg);
				indices.add(i);
			}
		}

		return new RelevantContent(relevantMessages, indices);
	}

	private List<Message> patchMessages(
		final List<Message> original,
		final List<Integer> originalIndices,
		final List<Message> transformed
	) {
		if (original.size() == transformed.size()) {
			return transformed;
		}

		return IntStream
			.range(0, original.size())
			.mapToObj(i -> {
				final var transformedIndex = originalIndices.indexOf(i);
				return transformedIndex != -1 ? transformed.get(transformedIndex) : original.get(i);
			})
			.collect(Collectors.toList());
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
	public Response<TextGuardResult> guardText(final TextGuardRequest request)
		throws PangeaException, PangeaAPIException {
		return guardText(request, false);
	}

	/**
	 * Text guard
	 * @pangea.description Guard text.
	 * @pangea.operationId ai_guard_post_v1_text_guard
	 * @param request Request parameters.
	 * @param onlyRelevantContent Whether or not to only send relevant content to AI Guard.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * final var response = client.guardText(TextGuardRequest.builder().text("hello world").build());
	 * }
	 */
	public Response<TextGuardResult> guardText(final TextGuardRequest request, final boolean onlyRelevantContent)
		throws PangeaException, PangeaAPIException {
		if (request.getText() == null && request.getMessages() == null) {
			throw new IllegalArgumentException("One of `text` or `messages` must be provided");
		}

		if (onlyRelevantContent && request.getMessages() != null) {
			final var originalMessages = request.getMessages();
			final var relevantContent = getRelevantContent(originalMessages);

			final var relevantRequest = request.withMessages(relevantContent.getMessages());

			final var response = post(
				"/v1/text/guard",
				relevantRequest,
				new TypeReference<Response<TextGuardResult>>() {}
			);

			final var result = response.getResult();
			if (result != null && result.getPromptMessages() != null) {
				final var transformed = patchMessages(
					originalMessages,
					relevantContent.getIndices(),
					result.getPromptMessages()
				);
				result.setPromptMessages(transformed);
			}

			return response;
		}

		return post("/v1/text/guard", request, new TypeReference<Response<TextGuardResult>>() {});
	}

	/**
	 * Guard LLM input and output
	 * @pangea.description Analyze and redact content to avoid manipulation of
	 *   the model, addition of malicious content, and other undesirable data transfers.
	 * @pangea.operationId ai_guard_post_v1_guard
	 * @param request Request parameters.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 */
	public Response<GuardResult> guard(final GuardRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/guard", request, new TypeReference<Response<GuardResult>>() {});
	}
}
