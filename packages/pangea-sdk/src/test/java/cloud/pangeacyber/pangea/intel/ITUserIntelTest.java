package cloud.pangeacyber.pangea.intel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.models.HashType;
import cloud.pangeacyber.pangea.intel.models.UserBreachedBulkData;
import cloud.pangeacyber.pangea.intel.models.UserBreachedData;
import cloud.pangeacyber.pangea.intel.models.UserPasswordBreachedBulkData;
import cloud.pangeacyber.pangea.intel.models.UserPasswordBreachedData;
import cloud.pangeacyber.pangea.intel.requests.UserBreachedBulkRequest;
import cloud.pangeacyber.pangea.intel.requests.UserBreachedRequest;
import cloud.pangeacyber.pangea.intel.requests.UserPasswordBreachedBulkRequest;
import cloud.pangeacyber.pangea.intel.requests.UserPasswordBreachedRequest;
import cloud.pangeacyber.pangea.intel.responses.UserBreachedBulkResponse;
import cloud.pangeacyber.pangea.intel.responses.UserBreachedResponse;
import cloud.pangeacyber.pangea.intel.responses.UserPasswordBreachedBulkResponse;
import cloud.pangeacyber.pangea.intel.responses.UserPasswordBreachedResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ITUserIntelTest {

	UserIntelClient client;
	static TestEnvironment environment;

	@BeforeAll
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("user-intel", TestEnvironment.LIVE);
	}

	@BeforeEach
	public void setUp() throws Exception {
		client = new UserIntelClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
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
	public void testUserBreached_ByPhoneNumbersBulk() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		UserBreachedBulkResponse response = client.breachedBulk(
			new UserBreachedBulkRequest.Builder().phoneNumbers(new String[] { "8005550123", "8005550124" }).build()
		);
		assertTrue(response.isOk());

		UserBreachedBulkData data = response.getResult().getData();
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertEquals(2, data.size());
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
	public void testUserBreached_ByEmailBulk() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		UserBreachedBulkResponse response = client.breachedBulk(
			new UserBreachedBulkRequest.Builder()
				.emails(new String[] { "test@example.com", "noreply@example.com" })
				.build()
		);
		assertTrue(response.isOk());

		UserBreachedBulkData data = response.getResult().getData();
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertEquals(2, data.size());
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
	public void testUserBreached_ByUsernamesBulk() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		UserBreachedBulkResponse response = client.breachedBulk(
			new UserBreachedBulkRequest.Builder().usernames(new String[] { "shortpatrick", "user1" }).build()
		);
		assertTrue(response.isOk());

		UserBreachedBulkData data = response.getResult().getData();
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertEquals(2, data.size());
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
	public void testUserBreached_ByIPBulk() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		UserBreachedBulkResponse response = client.breachedBulk(
			new UserBreachedBulkRequest.Builder().ips(new String[] { "192.168.140.37", "1.1.1.1" }).build()
		);
		assertTrue(response.isOk());

		UserBreachedBulkData data = response.getResult().getData();
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertEquals(2, data.size());
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

	@Test
	public void testUserPasswordBreached_Bulk() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		UserPasswordBreachedBulkResponse response = client.breachedBulk(
			new UserPasswordBreachedBulkRequest.Builder(HashType.SHA256, new String[] { "5baa6", "5baa7" }).build()
		);
		assertTrue(response.isOk());

		UserPasswordBreachedBulkData data = response.getResult().getData();
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertEquals(2, data.size());
	}
}
