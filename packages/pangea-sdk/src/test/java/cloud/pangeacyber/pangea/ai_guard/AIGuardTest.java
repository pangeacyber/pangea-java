package cloud.pangeacyber.pangea.ai_guard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.ai_guard.models.Message;
import cloud.pangeacyber.pangea.ai_guard.requests.GuardRequest;
import cloud.pangeacyber.pangea.ai_guard.requests.TextGuardRequest;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

final class AIGuardTest {

	static final String BASE_URL = System.getenv("TEST_API_BASE_URL") != null
		? System.getenv("TEST_API_BASE_URL")
		: "http://localhost:4010";

	private static void checkTestServer() throws TestAbortedException {
		try {
			URI.create(BASE_URL).toURL().openConnection().connect();
		} catch (Exception error) {
			throw new TestAbortedException("Mock OpenAPI server is not running. Skipping tests.", error);
		}
	}

	@Test
	void guardText() throws PangeaException, PangeaAPIException {
		checkTestServer();

		final var client = new AIGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.guardText(
			TextGuardRequest.builder().text("hello world").recipe("pangea_prompt_guard").build()
		);
		assertTrue(response.isOk());
		assertNotNull(response.getResult());
	}

	@Test
	void guardMessages() throws PangeaException, PangeaAPIException {
		checkTestServer();

		final var client = new AIGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.guardText(
			TextGuardRequest
				.builder()
				.messages(
					List.of(
						Message.builder().role("user").content("hello world").build(),
						Message.builder().role("assistant").content("hello world").build()
					)
				)
				.recipe("pangea_prompt_guard")
				.build()
		);
		assertTrue(response.isOk());
		assertNotNull(response.getResult());
	}

	@Test
	void guard() throws PangeaException, PangeaAPIException {
		checkTestServer();

		final var client = new AIGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.guard(
			GuardRequest
				.builder()
				.input(Map.of("messages", List.of(Message.builder().role("user").content("hello world").build())))
				.recipe("pangea_prompt_guard")
				.build()
		);
		assertTrue(response.isOk());
		assertEquals(200, response.getHttpResponse().getCode());
		assertNotNull(response.getResult());
	}
}
