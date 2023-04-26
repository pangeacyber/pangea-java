package cloud.pangeacyber.pangea.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.audit.AuditClient.AuditClientBuilder;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.SignerException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ITAuditTest {

	Config cfg;
	AuditClient client, localSignClient, localSignInfoClient, vaultSignClient, signNtenandIDClient, customSchemaClient, localSignCustomSchemaClient;
	TestEnvironment environment = TestEnvironment.DEVELOP;
	CustomEvent customEvent;

	private static final String ACTOR = "java-sdk";
	private static final String MSG_NO_SIGNED = "test-message";
	private static final String MSG_SIGNED_LOCAL = "sign-test-local";
	private static final String MSG_SIGNED_VAULT = "sign-test-vault";
	private static final String STATUS_NO_SIGNED = "no-signed";
	private static final String STATUS_SIGNED = "signed";
	private static final String MSG_CUSTOM_SCHEMA_NO_SIGNED = "java-sdk-custom-schema-no-signed";
	private static final String MSG_CUSTOM_SCHEMA_SIGNED_LOCAL = "java-sdk-custom-schema-sign-local";
	private static final String MSG_CUSTOM_SCHEMA_SIGNED_VAULT = "java-sdk-custom-schema-sign-vault";
	private static final String LONG_FIELD =
		"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed lacinia, orci eget commodo commodo non.";

	@Before
	public void setUp() throws ConfigException {
		Config vaultCfg = Config.fromVaultIntegrationEnvironment(environment);
		this.cfg = Config.fromIntegrationEnvironment(environment);
		Config customSchemaCfg = Config.fromCustomSchemaIntegrationEnvironment(environment);
		Map<String, Object> pkInfo = new LinkedHashMap<String, Object>();
		pkInfo.put("ExtraInfo", "LocalKey");

		client = new AuditClientBuilder(cfg).build();
		vaultSignClient = new AuditClientBuilder(vaultCfg).build();
		customSchemaClient = new AuditClientBuilder(customSchemaCfg).build();

		localSignClient =
			new AuditClientBuilder(cfg)
				.withPrivateKey("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey")
				.build();

		localSignCustomSchemaClient =
			new AuditClientBuilder(customSchemaCfg)
				.withPrivateKey("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey")
				.build();

		signNtenandIDClient =
			new AuditClientBuilder(cfg)
				.withTenantID("mytenantid")
				.withPrivateKey("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey")
				.build();
		localSignInfoClient =
			new AuditClientBuilder(cfg)
				.withPrivateKey("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey")
				.withPkInfo(pkInfo)
				.build();

		customEvent =
			new CustomEvent.Builder(MSG_CUSTOM_SCHEMA_NO_SIGNED)
				.fieldInt(1)
				.fieldBool(true)
				.fieldStrShort(STATUS_NO_SIGNED)
				.fieldStrLong(LONG_FIELD)
				.build();
	}

	@Test
	public void testLog() throws PangeaException, PangeaAPIException {
		Event event = new Event(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		LogResponse response = client.log(event, Event.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(result.getConsistencyVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getMembershipVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getSignatureVerification(), EventVerification.NOT_VERIFIED);
	}

	@Test
	public void testLog_customSchema() throws PangeaException, PangeaAPIException {
		LogResponse response = customSchemaClient.log(customEvent, CustomEvent.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(result.getConsistencyVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getMembershipVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getSignatureVerification(), EventVerification.NOT_VERIFIED);
	}

	@Test
	public void testLogNoVerbose() throws PangeaException, PangeaAPIException {
		Event event = new Event(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		LogResponse response = client.log(event, false, false, false, Event.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
	}

	@Test
	public void testLogNoVerbose_customSchema() throws PangeaException, PangeaAPIException {
		LogResponse response = customSchemaClient.log(customEvent, false, false, false, CustomEvent.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
	}

	@Test
	public void testLogVerbose() throws PangeaAPIException, PangeaException {
		Event event = new Event(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		try {
			LogResponse response = client.log(event, false, true, false, Event.class);
			assertTrue(response.isOk());

			LogResult result = response.getResult();
			assertNotNull(result.getEventEnvelope());
			assertNotNull(result.getHash());
			Event eventResult = (Event) result.getEventEnvelope().getEvent();
			assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
			assertNull(result.getConsistencyProof());
			assertNotNull(result.getMembershipProof());
			assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
			assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
		}
	}

	@Test
	public void testLogVerbose_customSchema() throws PangeaAPIException, PangeaException {
		try {
			LogResponse response = customSchemaClient.log(customEvent, false, true, false, CustomEvent.class);
			assertTrue(response.isOk());

			LogResult result = response.getResult();
			assertNotNull(result.getEventEnvelope());
			assertNotNull(result.getHash());
			CustomEvent eventResult = (CustomEvent) result.getEventEnvelope().getEvent();
			assertEquals(MSG_CUSTOM_SCHEMA_NO_SIGNED, eventResult.getMessage());
			assertEquals(Integer.valueOf(1), eventResult.getFieldInt());
			assertEquals(true, eventResult.getFieldBool());
			assertEquals(STATUS_NO_SIGNED, eventResult.getFieldStrShort());
			assertEquals(LONG_FIELD, eventResult.getFieldStrLong());
			assertNull(result.getConsistencyProof());
			assertNotNull(result.getMembershipProof());
			assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
			assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
		}
	}

	@Test
	public void testLogTenantID() throws PangeaAPIException, PangeaException, ConfigException {
		Event event = new Event(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		LogResponse response = signNtenandIDClient.log(event, false, true, false, Event.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		Event eventResult = (Event) result.getEventEnvelope().getEvent();
		assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
		assertNull(result.getConsistencyProof());
		assertNotNull(result.getMembershipProof());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
		assertEquals("mytenantid", eventResult.getTenantID());
	}

	@Test
	public void testLogVerify() throws PangeaAPIException, PangeaException {
		Event event = new Event(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		LogResponse response = client.log(event, false, true, true, Event.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		Event eventResult = (Event) result.getEventEnvelope().getEvent();
		assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());

		// Second log
		event = new Event(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);
		response = client.log(event, false, true, true, Event.class);
		assertTrue(response.isOk());

		result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		eventResult = (Event) result.getEventEnvelope().getEvent();
		assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
		assertEquals(EventVerification.SUCCESS, result.getConsistencyVerification());
		assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
	}

	@Test
	public void testLogVerify_customSchema() throws PangeaAPIException, PangeaException {
		LogResponse response = customSchemaClient.log(customEvent, false, true, true, CustomEvent.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		CustomEvent eventResult = (CustomEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_CUSTOM_SCHEMA_NO_SIGNED, eventResult.getMessage());
		assertEquals(Integer.valueOf(1), eventResult.getFieldInt());
		assertEquals(true, eventResult.getFieldBool());
		assertEquals(STATUS_NO_SIGNED, eventResult.getFieldStrShort());
		assertEquals(LONG_FIELD, eventResult.getFieldStrLong());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());

		// Second log
		response = customSchemaClient.log(customEvent, false, true, true, CustomEvent.class);
		assertTrue(response.isOk());

		result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		eventResult = (CustomEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_CUSTOM_SCHEMA_NO_SIGNED, eventResult.getMessage());
		assertEquals(Integer.valueOf(1), eventResult.getFieldInt());
		assertEquals(true, eventResult.getFieldBool());
		assertEquals(STATUS_NO_SIGNED, eventResult.getFieldStrShort());
		assertEquals(LONG_FIELD, eventResult.getFieldStrLong());
		assertEquals(EventVerification.SUCCESS, result.getConsistencyVerification());
		assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
	}

	@Test
	public void testLogLocalSignature() throws PangeaException, PangeaAPIException, ConfigException {
		Event event = new Event(MSG_SIGNED_LOCAL);
		event.setActor(ACTOR);
		event.setAction("Action");
		event.setSource("Source");
		event.setStatus(STATUS_SIGNED);
		event.setTarget("Target");
		event.setNewField("New");
		event.setOld("Old");

		LogResponse response = localSignClient.log(event, true, true, true, Event.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		Event eventResult = (Event) result.getEventEnvelope().getEvent();
		assertEquals(MSG_SIGNED_LOCAL, eventResult.getMessage());
		assertEquals(
			"""
{"key":"-----BEGIN PUBLIC KEY-----\\nMCowBQYDK2VwAyEAlvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=\\n-----END PUBLIC KEY-----\\n"}""",
			result.getEventEnvelope().getPublicKey()
		);
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
	}

	@Test
	public void testLogLocalSignature_customSchema() throws PangeaException, PangeaAPIException, ConfigException {
		CustomEvent event = new CustomEvent.Builder(MSG_CUSTOM_SCHEMA_SIGNED_LOCAL)
			.fieldInt(1)
			.fieldBool(true)
			.fieldStrShort(STATUS_SIGNED)
			.fieldStrLong(LONG_FIELD)
			.build();

		LogResponse response = localSignCustomSchemaClient.log(event, true, true, true, CustomEvent.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		CustomEvent eventResult = (CustomEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_CUSTOM_SCHEMA_SIGNED_LOCAL, eventResult.getMessage());
		assertEquals(
			"""
{"key":"-----BEGIN PUBLIC KEY-----\\nMCowBQYDK2VwAyEAlvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=\\n-----END PUBLIC KEY-----\\n"}""",
			result.getEventEnvelope().getPublicKey()
		);
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
	}

	@Test
	public void testLogLocalSignatureWithPublicKeyInfo() throws PangeaException, PangeaAPIException, ConfigException {
		Event event = new Event(MSG_SIGNED_LOCAL);
		event.setActor(ACTOR);
		event.setAction("Action");
		event.setSource("Source");
		event.setStatus(STATUS_SIGNED);
		event.setTarget("Target");
		event.setNewField("New");
		event.setOld("Old");

		LogResponse response = localSignInfoClient.log(event, true, true, true, Event.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		Event eventResult = (Event) result.getEventEnvelope().getEvent();
		assertEquals(MSG_SIGNED_LOCAL, eventResult.getMessage());
		assertEquals(
			"""
{"ExtraInfo":"LocalKey","key":"-----BEGIN PUBLIC KEY-----\\nMCowBQYDK2VwAyEAlvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=\\n-----END PUBLIC KEY-----\\n"}""",
			result.getEventEnvelope().getPublicKey()
		);
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
	}

	@Test
	public void testLogVaultSignature() throws PangeaException, PangeaAPIException, ConfigException {
		Event event = new Event(MSG_SIGNED_VAULT);
		event.setActor(ACTOR);
		event.setAction("Action");
		event.setSource("Source");
		event.setStatus(STATUS_SIGNED);
		event.setTarget("Target");
		event.setNewField("New");
		event.setOld("Old");

		LogResponse response = vaultSignClient.log(event, false, true, true, Event.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		Event eventResult = (Event) result.getEventEnvelope().getEvent();
		assertEquals(MSG_SIGNED_VAULT, eventResult.getMessage());
		assertNotNull(result.getEventEnvelope().getPublicKey());
		assertNotNull(result.getEventEnvelope().getSignature());
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
	}

	@Test
	public void testLogLocalSignatureAndTenantID() throws PangeaException, PangeaAPIException, ConfigException {
		Event event = new Event(MSG_SIGNED_LOCAL);
		event.setActor(ACTOR);
		event.setAction("Action");
		event.setSource("Source");
		event.setStatus(STATUS_SIGNED);
		event.setTarget("Target");
		event.setNewField("New");
		event.setOld("Old");

		LogResponse response = signNtenandIDClient.log(event, true, true, true, Event.class);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		Event eventResult = (Event) result.getEventEnvelope().getEvent();
		assertEquals(MSG_SIGNED_LOCAL, eventResult.getMessage());
		assertEquals(
			"""
{"key":"-----BEGIN PUBLIC KEY-----\\nMCowBQYDK2VwAyEAlvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=\\n-----END PUBLIC KEY-----\\n"}""",
			result.getEventEnvelope().getPublicKey()
		);
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
		assertEquals("mytenantid", eventResult.getTenantID());
	}

	@Test
	public void testSearchDefault() throws PangeaException, PangeaAPIException {
		int limit = 4;
		int maxResults = 6;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(maxResults)
			.limit(limit)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = client.search(request, Event.class, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification()); // This could be NOT_VERIFIED or SUCCESS is they have data or not
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
			assertNotNull(event.getEventEnvelope());
			assertNotNull(event.getHash());
		}
	}

	@Test
	public void testSearchDefault_customSchema() throws PangeaException, PangeaAPIException {
		int limit = 4;
		int maxResults = 6;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(maxResults)
			.limit(limit)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = customSchemaClient.search(request, CustomEvent.class, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification()); // This could be NOT_VERIFIED or SUCCESS is they have data or not
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
			assertNotNull(event.getEventEnvelope());
			assertNotNull(event.getHash());
		}
	}

	@Test
	public void testSearchNoVerify() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:Integration test msg")
			.maxResults(maxResults)
			.order("desc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = client.search(request, Event.class, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getSignatureVerification());
		}
	}

	@Test
	public void testSearchNoVerify_customEvent() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:" + MSG_CUSTOM_SCHEMA_NO_SIGNED)
			.maxResults(maxResults)
			.order("desc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = customSchemaClient.search(request, CustomEvent.class, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getSignatureVerification());
		}
	}

	@Test
	public void testSearchVerifyConsistency() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"").maxResults(maxResults).order("asc").build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).build();

		SearchResponse response = client.search(request, Event.class, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
			assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
		}
	}

	@Test
	public void testSearchVerifyConsistency_customEvent() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"").maxResults(maxResults).order("asc").build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).build();

		SearchResponse response = customSchemaClient.search(request, CustomEvent.class, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
			assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
		}
	}

	@Test
	public void testSearchVerifySignature() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:" + MSG_SIGNED_LOCAL + " status:" + STATUS_SIGNED)
			.maxResults(maxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = client.search(request, Event.class, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getSignatureVerification());
		}
	}

	@Test
	public void testSearchVerifySignature_customEvent() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:" + MSG_CUSTOM_SCHEMA_SIGNED_LOCAL)
			.maxResults(maxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = customSchemaClient.search(request, CustomEvent.class, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getSignatureVerification());
		}
	}

	@Test
	public void testResultsDefault() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;

		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse searchResponse = client.search(request, Event.class, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;
		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();
		ResultsResponse resultsResponse = client.results(resultRequest, Event.class, config);
		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
		}
	}

	public void testResultsVerify() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).build();

		SearchResponse searchResponse = client.search(request, Event.class, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;

		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();

		ResultsResponse resultsResponse = client.results(resultRequest, Event.class, config);

		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
			assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
		}
	}

	@Test
	public void testResultsNoVerify() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).verifyEvents(true).build();

		SearchResponse searchResponse = client.search(request, Event.class, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;
		// Skip verifications
		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();

		config = new SearchConfig.Builder().verifyConsistency(false).build();
		ResultsResponse resultsResponse = client.results(resultRequest, Event.class, config);

		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			// This should be NOT_VERIFIED
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
		}
	}

	@Test
	public void testResultsDefault_customEvent() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;

		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse searchResponse = customSchemaClient.search(request, CustomEvent.class, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;
		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();
		ResultsResponse resultsResponse = customSchemaClient.results(resultRequest, CustomEvent.class, config);
		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
		}
	}

	public void testResultsVerify_customEvent() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).build();

		SearchResponse searchResponse = customSchemaClient.search(request, CustomEvent.class, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;

		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();

		ResultsResponse resultsResponse = customSchemaClient.results(resultRequest, CustomEvent.class, config);

		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
			assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
		}
	}

	@Test
	public void testResultsNoVerify_customEvent() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).verifyEvents(true).build();

		SearchResponse searchResponse = customSchemaClient.search(request, CustomEvent.class, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;
		// Skip verifications
		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();

		config = new SearchConfig.Builder().verifyConsistency(false).build();
		ResultsResponse resultsResponse = customSchemaClient.results(resultRequest, CustomEvent.class, config);

		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			// This should be NOT_VERIFIED
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
		}
	}

	@Test
	public void testRoot() throws PangeaException, PangeaAPIException {
		RootResponse response = client.getRoot();
		assertTrue(response.isOk());

		RootResult result = response.getResult();
		Root root = result.getRoot();
		assertNotNull(root);
		assertNotNull(root.getSize());
		assertNotNull(root.getTreeName());
		assertNotNull(root.getRootHash());
	}

	@Test
	public void testRootWithSize() throws PangeaException, PangeaAPIException {
		int treeSize = 2;
		RootResponse response = client.getRoot(treeSize);
		assertTrue(response.isOk());

		RootResult result = response.getResult();
		Root root = result.getRoot();
		assertNotNull(root);
		assertNotNull(root.getSize());
		assertNotNull(root.getTreeName());
		assertNotNull(root.getRootHash());
		assertEquals(treeSize, root.getSize());
	}

	@Test(expected = PangeaAPIException.class)
	public void testRootTreeNotFound() throws PangeaException, PangeaAPIException {
		int treeSize = 1000000;
		RootResponse response = client.getRoot(treeSize);
	}

	@Test(expected = UnauthorizedException.class)
	public void testRootUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		int treeSize = 1;
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setToken("notarealtoken");
		AuditClient fakeClient = new AuditClientBuilder(cfg).build();
		RootResponse response = fakeClient.getRoot(treeSize);
	}

	@Test(expected = UnauthorizedException.class)
	public void testLogUnathorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setToken("notarealtoken");
		AuditClient fakeClient = new AuditClientBuilder(cfg).build();
		Event event = new Event("Test msg");
		LogResponse response = fakeClient.log(event, Event.class);
	}

	// @Test(expected = ValidationException.class)
	// public void testLogEmptyMessage() throws PangeaException, PangeaAPIException {
	// 	Event event = new Event("");
	// 	LogResponse response = client.log(event);
	// }

	@Test(expected = ValidationException.class)
	public void testSearchValidationException() throws PangeaAPIException, PangeaException {
		SearchRequest request = new SearchRequest.Builder("message:\"\"").order("notavalidorder").build();

		SearchConfig config = new SearchConfig.Builder().build();
		SearchResponse searchResponse = client.search(request, Event.class, config);
	}

	@Test(expected = UnauthorizedException.class)
	public void testSearchValidationException2() throws PangeaAPIException, PangeaException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setToken("notarealtoken");
		AuditClient fakeClient = new AuditClientBuilder(cfg).build();

		SearchRequest request = new SearchRequest.Builder("message:\"\"").build();
		SearchConfig config = new SearchConfig.Builder().build();
		SearchResponse searchResponse = fakeClient.search(request, Event.class, config);
	}

	@Test(expected = SignerException.class)
	public void testLogSignerNotSet() throws PangeaException, PangeaAPIException, ConfigException {
		Event event = new Event(MSG_NO_SIGNED);
		LogResponse response = client.log(event, true, true, true, Event.class);
	}
}
