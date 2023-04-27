package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.models.HashType;
import cloud.pangeacyber.pangea.intel.models.UserBreachedData;
import cloud.pangeacyber.pangea.intel.models.UserPasswordBreachedData;
import cloud.pangeacyber.pangea.intel.requests.UserBreachedRequest;
import cloud.pangeacyber.pangea.intel.requests.UserPasswordBreachedRequest;
import cloud.pangeacyber.pangea.intel.responses.UserBreachedResponse;
import cloud.pangeacyber.pangea.intel.responses.UserPasswordBreachedResponse;
import org.junit.Before;
import org.junit.Test;

public class ITUserIntelTest {

	UserIntelClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
		client = new UserIntelClient(Config.fromIntegrationEnvironment(environment));
		client.setCustomUserAgent("test");
	}

	@Test
	public void testUserBreached_1() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		UserBreachedResponse response = client.breached(
			new UserBreachedRequest.Builder().phoneNumber("8005550123").build()
		);
		assertTrue(response.isOk());

		UserBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUserBreached_2() throws PangeaException, PangeaException, PangeaAPIException {
		// provider, not verbose by default, not raw by default;
		UserBreachedResponse response = client.breached(
			new UserBreachedRequest.Builder().phoneNumber("8005550123").provider("spycloud").build()
		);
		assertTrue(response.isOk());

		UserBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUserBreached_3() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, verbose true, not raw by default;
		UserBreachedResponse response = client.breached(
			new UserBreachedRequest.Builder().phoneNumber("8005550123").verbose(true).build()
		);
		assertTrue(response.isOk());

		UserBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUserBreached_4() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, raw true;
		UserBreachedResponse response = client.breached(
			new UserBreachedRequest.Builder().phoneNumber("8005550123").raw(true).build()
		);
		assertTrue(response.isOk());

		UserBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testUserBreached_ByEmail() throws PangeaException, PangeaException, PangeaAPIException {
		// provider, not verbose by default, not raw by default;
		UserBreachedResponse response = client.breached(
			new UserBreachedRequest.Builder().email("test@example.com").provider("spycloud").build()
		);
		assertTrue(response.isOk());

		UserBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUserBreached_ByUsername() throws PangeaException, PangeaException, PangeaAPIException {
		// provider, not verbose by default, not raw by default;
		UserBreachedResponse response = client.breached(
			new UserBreachedRequest.Builder().username("shortpatrick").provider("spycloud").build()
		);
		assertTrue(response.isOk());

		UserBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUserBreached_ByIP() throws PangeaException, PangeaException, PangeaAPIException {
		// provider, not verbose by default, not raw by default;
		UserBreachedResponse response = client.breached(
			new UserBreachedRequest.Builder().ip("192.168.140.37").provider("spycloud").build()
		);
		assertTrue(response.isOk());

		UserBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUserPasswordBreached_1() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		UserPasswordBreachedResponse response = client.breached(
			new UserPasswordBreachedRequest.Builder(HashType.SHA256, "5baa6").build()
		);
		assertTrue(response.isOk());

		UserPasswordBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUserPasswordBreached_2() throws PangeaException, PangeaException, PangeaAPIException {
		// provider, not verbose by default, not raw by default;
		UserPasswordBreachedResponse response = client.breached(
			new UserPasswordBreachedRequest.Builder(HashType.SHA256, "5baa6").provider("spycloud").build()
		);
		assertTrue(response.isOk());

		UserPasswordBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUserPasswordBreached_3() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, verbose true, not raw by default;
		UserPasswordBreachedResponse response = client.breached(
			new UserPasswordBreachedRequest.Builder(HashType.SHA256, "5baa6").verbose(true).build()
		);
		assertTrue(response.isOk());

		UserPasswordBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUserPasswordBreached_4() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, raw true;
		UserPasswordBreachedResponse response = client.breached(
			new UserPasswordBreachedRequest.Builder(HashType.SHA256, "5baa6").raw(true).build()
		);
		assertTrue(response.isOk());

		UserPasswordBreachedData data = response.getResult().getData();
		assertTrue(data.getFoundInBreach());
		assertTrue(data.getBreachCount() > 0);
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}
}
