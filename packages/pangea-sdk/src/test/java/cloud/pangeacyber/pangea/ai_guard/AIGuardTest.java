package cloud.pangeacyber.pangea.ai_guard;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.ai_guard.models.ServiceConfigListFilter;
import cloud.pangeacyber.pangea.ai_guard.requests.CreateServiceConfigParams;
import cloud.pangeacyber.pangea.ai_guard.requests.DeleteServiceConfigParams;
import cloud.pangeacyber.pangea.ai_guard.requests.GetServiceConfigParams;
import cloud.pangeacyber.pangea.ai_guard.requests.ListServiceConfigsParams;
import cloud.pangeacyber.pangea.ai_guard.requests.TextGuardRequest;
import cloud.pangeacyber.pangea.ai_guard.requests.UpdateServiceConfigParams;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.prompt_guard.models.Message;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
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
				.messages(List.of(new Message("user", "hello world"), new Message("assistant", "hello world")))
				.recipe("pangea_prompt_guard")
				.build()
		);
		assertTrue(response.isOk());
		assertNotNull(response.getResult());
	}

	@Test
	void getServiceConfig() throws PangeaException, PangeaAPIException {
		final var client = new AIGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.getServiceConfig(GetServiceConfigParams.builder().id("my id").build());
		assertTrue(response.isOk());
		assertNotNull(response.getResult());
	}

	@Test
	void createServiceConfig() throws PangeaException, PangeaAPIException {
		final var client = new AIGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.createServiceConfig(CreateServiceConfigParams.builder().name("my name").build());
		assertTrue(response.isOk());
		assertNotNull(response.getResult());
	}

	@Test
	void updateServiceConfig() throws PangeaException, PangeaAPIException {
		final var client = new AIGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.updateServiceConfig(
			UpdateServiceConfigParams.builder().id("id").name("name").build()
		);
		assertTrue(response.isOk());
		assertNotNull(response.getResult());
		assertNotNull(response.getResult().getId());
	}

	@Test
	void deleteServiceConfig() throws PangeaException, PangeaAPIException {
		final var client = new AIGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.deleteServiceConfig(DeleteServiceConfigParams.builder().id("my id").build());
		assertTrue(response.isOk());
		assertNotNull(response.getResult());
	}

	@Test
	void listServiceConfigs() throws PangeaException, PangeaAPIException {
		final var client = new AIGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.listServiceConfigs(
			ListServiceConfigsParams
				.builder()
				.filter(ServiceConfigListFilter.builder().id("my id").createdAt(OffsetDateTime.now()).build())
				.build()
		);
		assertTrue(response.isOk());
		assertNotNull(response.getResult());
		assertNotNull(response.getResult().getCount());
		assertNotNull(response.getResult().getItems());
	}
}
