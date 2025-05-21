package cloud.pangeacyber.pangea.prompt_guard;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.prompt_guard.models.ServiceConfigListFilter;
import cloud.pangeacyber.pangea.prompt_guard.requests.CreateServiceConfigParams;
import cloud.pangeacyber.pangea.prompt_guard.requests.DeleteServiceConfigParams;
import cloud.pangeacyber.pangea.prompt_guard.requests.GetServiceConfigParams;
import cloud.pangeacyber.pangea.prompt_guard.requests.ListServiceConfigsParams;
import cloud.pangeacyber.pangea.prompt_guard.requests.UpdateServiceConfigParams;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;

final class PromptGuardTest {

	static final String BASE_URL = System.getenv("TEST_API_BASE_URL") != null
		? System.getenv("TEST_API_BASE_URL")
		: "http://localhost:4010";

	@Test
	void getServiceConfig() throws PangeaException, PangeaAPIException {
		final var client = new PromptGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.getServiceConfig(GetServiceConfigParams.builder().id("id").build());
		assertTrue(response.isOk());
		assertNull(response.getResult());
	}

	@Test
	void createServiceConfig() throws PangeaException, PangeaAPIException {
		final var client = new PromptGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.createServiceConfig(CreateServiceConfigParams.builder().version("version").build());
		assertTrue(response.isOk());
		assertNull(response.getResult());
	}

	@Test
	void updateServiceConfig() throws PangeaException, PangeaAPIException {
		final var client = new PromptGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.updateServiceConfig(
			UpdateServiceConfigParams.builder().id("id").version("version").build()
		);
		assertTrue(response.isOk());
		assertNull(response.getResult());
	}

	@Test
	void deleteServiceConfig() throws PangeaException, PangeaAPIException {
		final var client = new PromptGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.deleteServiceConfig(DeleteServiceConfigParams.builder().id("id").build());
		assertTrue(response.isOk());
		assertNull(response.getResult());
	}

	@Test
	void listServiceConfigs() throws PangeaException, PangeaAPIException {
		final var client = new PromptGuardClient.Builder(
			Config.builder().baseUrlTemplate(BASE_URL).token("my api token").build()
		)
			.build();
		final var response = client.listServiceConfigs(
			ListServiceConfigsParams
				.builder()
				.filter(ServiceConfigListFilter.builder().id("id").createdAt(OffsetDateTime.now()).build())
				.build()
		);
		assertTrue(response.isOk());
		assertNotNull(response.getResult());
		assertNotNull(response.getResult().getCount());
		assertNotNull(response.getResult().getItems());
	}
}
