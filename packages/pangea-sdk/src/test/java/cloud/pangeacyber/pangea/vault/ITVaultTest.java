package cloud.pangeacyber.pangea.vault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.vault.models.AsymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.ItemVersionState;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.responses.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ITVaultTest {

	VaultClient client;
	TestEnvironment environment = TestEnvironment.LIVE;
	String time;
	Random random;
	final String actor = "JavaSDKTest";

	@Before
	public void setUp() throws ConfigException {
		client = new VaultClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		time = dtf.format(LocalDateTime.now());
		this.random = new Random();
	}

	private String getRandomID() {
		return String.valueOf(this.random.nextInt(1000000));
	}

	private String getName() {
		String callerName = new Throwable().getStackTrace()[1].getMethodName();
		return String.format("%s_%s_%s_%s", this.time, actor, callerName, getRandomID());
	}

	private void encryptingCycle(String id) throws PangeaException, PangeaException, PangeaAPIException {
		String message = "thisisamessagetoencrypt";
		String dataB64 = Utils.stringToStringB64(message);

		// Encrypt 1
		EncryptResponse encryptResponse1 = client.encrypt(new EncryptRequest.Builder(id, dataB64).build());
		assertEquals(id, encryptResponse1.getResult().getId());
		assertEquals(Integer.valueOf(1), encryptResponse1.getResult().getVersion());
		assertNotNull(encryptResponse1.getResult().getCipherText());

		// Rotate
		KeyRotateResponse rotateResponse = client.keyRotate(
			new KeyRotateRequest.Builder(id, ItemVersionState.SUSPENDED).build()
		);
		assertEquals(Integer.valueOf(2), rotateResponse.getResult().getVersion());
		assertEquals(id, rotateResponse.getResult().getId());

		// Encrypt 2
		EncryptResponse encryptResponse2 = client.encrypt(new EncryptRequest.Builder(id, dataB64).version(2).build());
		assertEquals(id, encryptResponse1.getResult().getId());
		assertEquals(Integer.valueOf(2), encryptResponse2.getResult().getVersion());
		assertNotNull(encryptResponse2.getResult().getCipherText());

		// Decrypt 1
		DecryptResponse decryptResponse1 = client.decrypt(
			new DecryptRequest.Builder(id, encryptResponse1.getResult().getCipherText()).version(1).build()
		);
		assertEquals(dataB64, decryptResponse1.getResult().getPlainText());

		// Decrypt 2
		DecryptResponse decryptResponse2 = client.decrypt(
			new DecryptRequest.Builder(id, encryptResponse2.getResult().getCipherText()).version(2).build()
		);
		assertEquals(dataB64, decryptResponse2.getResult().getPlainText());

		// Decrypt default
		DecryptResponse decryptResponseDefault = client.decrypt(
			new DecryptRequest.Builder(id, encryptResponse2.getResult().getCipherText()).build()
		);
		assertEquals(dataB64, decryptResponseDefault.getResult().getPlainText());

		// Add failure cases. Wrong ID, wrong version, etc

		// Suspend key
		StateChangeResponse stateChangeResponse = client.stateChange(id, 1, ItemVersionState.DEACTIVATED);
		assertEquals(id, stateChangeResponse.getResult().getId());

		// Decrypt after revoke
		DecryptResponse decryptResponseAfterSuspend = client.decrypt(
			new DecryptRequest.Builder(id, encryptResponse1.getResult().getCipherText()).version(1).build()
		);
		assertEquals(dataB64, decryptResponseAfterSuspend.getResult().getPlainText());
	}

	private void asymSigningCycle(String id) throws PangeaException, PangeaException, PangeaAPIException {
		String message = "thisisamessagetosign";
		String data = Utils.stringToStringB64(message);

		// Sign 1
		SignResponse signResponse1 = client.sign(id, data);
		assertEquals(Integer.valueOf(1), signResponse1.getResult().getVersion());
		assertNotNull(signResponse1.getResult().getSignature());

		// Rotate
		KeyRotateResponse rotateResponse = client.keyRotate(
			new KeyRotateRequest.Builder(id, ItemVersionState.SUSPENDED).build()
		);
		assertEquals(Integer.valueOf(2), rotateResponse.getResult().getVersion());
		assertNotNull(rotateResponse.getResult().getEncodedPublicKey());

		// Sign 2
		SignResponse signResponse2 = client.sign(id, data);
		assertEquals(Integer.valueOf(2), signResponse2.getResult().getVersion());
		assertNotNull(signResponse2.getResult().getSignature());

		// Verify 1
		VerifyResponse verifyResponse1 = client.verify(id, data, signResponse1.getResult().getSignature(), 1);
		assertEquals(Integer.valueOf(1), verifyResponse1.getResult().getVersion());
		assertTrue(verifyResponse1.getResult().isValidSignature());

		// Verify 2
		VerifyResponse verifyResponse2 = client.verify(id, data, signResponse2.getResult().getSignature());
		assertEquals(Integer.valueOf(2), verifyResponse2.getResult().getVersion());
		assertTrue(verifyResponse2.getResult().isValidSignature());

		// TODO: Add failures cases

		// Wrong signature. Use signature of version 1, and try to verify with default version (2)
		VerifyResponse verifyResponseBad = client.verify(id, data, signResponse1.getResult().getSignature());
		assertFalse(verifyResponseBad.getResult().isValidSignature());

		// Suspend key
		StateChangeResponse stateChangeResponse = client.stateChange(id, 1, ItemVersionState.DEACTIVATED);
		assertEquals(id, stateChangeResponse.getResult().getId());

		// Verify after revoke
		VerifyResponse verifyResponseAfterSuspend = client.verify(
			id,
			data,
			signResponse1.getResult().getSignature(),
			1
		);
		assertEquals(Integer.valueOf(1), verifyResponseAfterSuspend.getResult().getVersion());
		assertTrue(verifyResponseAfterSuspend.getResult().isValidSignature());
	}

	private void jwtAsymSigningCycle(String id) throws PangeaException, PangeaException, PangeaAPIException {
		String payload = """
            {'message': 'message to sign', 'data': 'Some extra data'}
            """;

		// Sign 1
		JWTSignResponse signResponse1 = client.jwtSign(id, payload);
		assertNotNull(signResponse1.getResult().getJws());

		// Rotate
		KeyRotateResponse rotateResponse = client.keyRotate(
			new KeyRotateRequest.Builder(id, ItemVersionState.SUSPENDED).build()
		);
		assertEquals(Integer.valueOf(2), rotateResponse.getResult().getVersion());

		// Sign 2
		JWTSignResponse signResponse2 = client.jwtSign(id, payload);
		assertNotNull(signResponse2.getResult().getJws());

		// Verify 1
		JWTVerifyResponse verifyResponse1 = client.jwtVerify(signResponse1.getResult().getJws());
		assertTrue(verifyResponse1.getResult().isValidSignature());

		// Verify 2
		JWTVerifyResponse verifyResponse2 = client.jwtVerify(signResponse2.getResult().getJws());
		assertTrue(verifyResponse2.getResult().isValidSignature());

		// Gets default
		JWKGetResponse getResponse = client.jwkGet(id);
		assertEquals(1, getResponse.getResult().getKeys().length);

		// Gets all
		getResponse = client.jwkGet(id, "all");
		assertEquals(2, getResponse.getResult().getKeys().length);

		// Gets -1
		getResponse = client.jwkGet(id, "-1");
		assertEquals(2, getResponse.getResult().getKeys().length);

		// Suspend key
		StateChangeResponse stateChangeResponse = client.stateChange(id, 1, ItemVersionState.DEACTIVATED);
		assertEquals(id, stateChangeResponse.getResult().getId());

		// Verify after revoke
		JWTVerifyResponse verifyResponseAfterSuspend = client.jwtVerify(signResponse1.getResult().getJws());
		assertTrue(verifyResponseAfterSuspend.getResult().isValidSignature());
	}

	private void jwtSymSigningCycle(String id) throws PangeaException, PangeaException, PangeaAPIException {
		String payload = """
            {'message': 'message to sign', 'data': 'Some extra data'}
            """;

		// Sign 1
		JWTSignResponse signResponse1 = client.jwtSign(id, payload);
		assertNotNull(signResponse1.getResult().getJws());

		// Rotate
		KeyRotateResponse rotateResponse = client.keyRotate(
			new KeyRotateRequest.Builder(id, ItemVersionState.SUSPENDED).build()
		);
		assertEquals(Integer.valueOf(2), rotateResponse.getResult().getVersion());

		// Sign 2
		JWTSignResponse signResponse2 = client.jwtSign(id, payload);
		assertNotNull(signResponse2.getResult().getJws());

		// Verify 1
		JWTVerifyResponse verifyResponse1 = client.jwtVerify(signResponse1.getResult().getJws());
		assertTrue(verifyResponse1.getResult().isValidSignature());

		// Verify 2
		JWTVerifyResponse verifyResponse2 = client.jwtVerify(signResponse2.getResult().getJws());
		assertTrue(verifyResponse2.getResult().isValidSignature());

		// Suspend key
		StateChangeResponse stateChangeResponse = client.stateChange(id, 1, ItemVersionState.DEACTIVATED);
		assertEquals(id, stateChangeResponse.getResult().getId());

		// Verify after revoke
		JWTVerifyResponse verifyResponseAfterSuspend = client.jwtVerify(signResponse1.getResult().getJws());
		assertTrue(verifyResponseAfterSuspend.getResult().isValidSignature());
	}

	@Test
	public void testAESEncryptingLifeCycle() throws PangeaException, PangeaAPIException {
		String name = getName();
		try {
			SymmetricGenerateRequest generateRequest = new SymmetricGenerateRequest.Builder(
				SymmetricAlgorithm.AES,
				KeyPurpose.ENCRYPTION,
				name
			)
				.build();

			SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
			assertNotNull(generateResp.getResult().getId());
			assertEquals(Integer.valueOf(1), generateResp.getResult().getVersion());
			encryptingCycle(generateResp.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testEd25519SigningLifeCycle() throws PangeaException, PangeaAPIException {
		String name = getName();
		try {
			AsymmetricGenerateRequest generateRequest = new AsymmetricGenerateRequest.Builder(
				AsymmetricAlgorithm.ED25519,
				KeyPurpose.SIGNING,
				name
			)
				.build();

			// Generate
			AsymmetricGenerateResponse generateResp = client.asymmetricGenerate(generateRequest);
			assertNotNull(generateResp.getResult().getEncodedPublicKey());
			assertNotNull(generateResp.getResult().getId());
			assertEquals(Integer.valueOf(1), generateResp.getResult().getVersion());
			asymSigningCycle(generateResp.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testJWTasymES256SigningLifeCycle() throws PangeaException, PangeaAPIException {
		KeyPurpose purpose = KeyPurpose.JWT;
		AsymmetricAlgorithm algorithm = AsymmetricAlgorithm.ES256;
		String name = getName();
		try {
			AsymmetricGenerateRequest generateRequest = new AsymmetricGenerateRequest.Builder(algorithm, purpose, name)
				.build();

			// Generate
			AsymmetricGenerateResponse generateResp = client.asymmetricGenerate(generateRequest);
			assertNotNull(generateResp.getResult().getEncodedPublicKey());
			assertNotNull(generateResp.getResult().getId());
			assertEquals(Integer.valueOf(1), generateResp.getResult().getVersion());
			jwtAsymSigningCycle(generateResp.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testJWTsymHS256SigningLifeCycle() throws PangeaException, PangeaAPIException {
		KeyPurpose purpose = KeyPurpose.JWT;
		SymmetricAlgorithm algorithm = SymmetricAlgorithm.HS256;
		String name = getName();
		try {
			SymmetricGenerateRequest generateRequest = new SymmetricGenerateRequest.Builder(algorithm, purpose, name)
				.build();

			// Generate
			SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
			assertNotNull(generateResp.getResult().getId());
			assertEquals(Integer.valueOf(1), generateResp.getResult().getVersion());
			jwtSymSigningCycle(generateResp.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testSecretLifeCycle() throws PangeaException, PangeaAPIException {
		String secretV1 = "mysecret";
		String secretV2 = "mynewsecret";
		SecretStoreResponse storeResponse = null;
		String name = getName();
		try {
			storeResponse = client.secretStore(new SecretStoreRequest.Builder(secretV1, name).build());
			assertEquals(secretV1, storeResponse.getResult().getSecret());
			assertEquals(Integer.valueOf(1), storeResponse.getResult().getVersion());
			assertNotNull(storeResponse.getResult().getId());

			SecretRotateResponse rotateResponse = client.secretRotate(
				new SecretRotateRequest.Builder(storeResponse.getResult().getId(), secretV2)
					.rotationState(ItemVersionState.SUSPENDED)
					.build()
			);

			assertEquals(secretV2, rotateResponse.getResult().getSecret());
			assertEquals(Integer.valueOf(2), rotateResponse.getResult().getVersion());
			assertEquals(storeResponse.getResult().getId(), rotateResponse.getResult().getId());

			GetResponse getResponse = client.get(new GetRequest.Builder(storeResponse.getResult().getId()).build());
			assertEquals(secretV2, getResponse.getResult().getCurrentVersion().getSecret());

			StateChangeResponse stateChangeResponse = client.stateChange(
				storeResponse.getResult().getId(),
				1,
				ItemVersionState.DEACTIVATED
			);
			assertEquals(storeResponse.getResult().getId(), stateChangeResponse.getResult().getId());
			assertEquals(ItemVersionState.DEACTIVATED.toString(), stateChangeResponse.getResult().getState());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}

		GetResponse getReponse2 = client.get(new GetRequest.Builder(storeResponse.getResult().getId()).build());
		assertNotNull(getReponse2.getResult().getCurrentVersion().getSecret());
	}

	@Test
	public void testFolders() throws PangeaException, PangeaAPIException {
		final String FOLDER_PARENT = String.format("test_parent_folder_%s", this.time);
		final String FOLDER_NAME = "test_folder_name";
		final String FOLDER_NAME_NEW = "test_folder_name_new";

		try {
			// Create parent
			FolderCreateResponse createParentResp = client.folderCreate(
				new FolderCreateRequest.Builder(FOLDER_PARENT, "/").build()
			);
			String parentID = createParentResp.getResult().getId();
			assertNotNull(parentID);

			// Create folder
			FolderCreateResponse createFolderResp = client.folderCreate(
				new FolderCreateRequest.Builder(FOLDER_NAME, FOLDER_PARENT).build()
			);
			String folderID = createFolderResp.getResult().getId();
			assertNotNull(folderID);

			// Update name
			UpdateResponse updateResp = client.update(
				new UpdateRequest.Builder(folderID).name(FOLDER_NAME_NEW).build()
			);
			assertEquals(folderID, updateResp.getResult().getId());

			// List
			Map<String, String> filter = new LinkedHashMap<String, String>(1);
			filter.put("folder", FOLDER_PARENT);

			ListResponse listResp = client.list(new ListRequest.Builder().filter(filter).build());
			assertEquals(1, listResp.getResult().getCount());
			assertEquals(folderID, listResp.getResult().getItems().get(0).getId());
			assertEquals(FOLDER_NAME_NEW, listResp.getResult().getItems().get(0).getName());

			// Delete folder
			DeleteResponse deleteFolderResp = client.delete(folderID);
			assertEquals(folderID, deleteFolderResp.getResult().getId());

			// Delete folder
			DeleteResponse deleteParentResp = client.delete(parentID);
			assertEquals(parentID, deleteParentResp.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

    @Test
	public void testEncryptStructured() throws JsonProcessingException, JsonMappingException, PangeaException, PangeaAPIException {
        // Test data.
        String payload = """
            { "field1": ["1", "2", "true", "false"], "field2": "data" }
        """;
        var data = new ObjectMapper().readValue(payload, new TypeReference<Map<String, Object>>() {});

        // Generate an encryption key.
        var generateRequest = new SymmetricGenerateRequest.Builder(
            SymmetricAlgorithm.AES256_CFB,
            KeyPurpose.ENCRYPTION,
            getName()
        ).build();
        var generateResp = client.symmetricGenerate(generateRequest);
        var key = generateResp.getResult().getId();
        assertNotNull(key);

        // Encrypt.
        var request = new EncryptStructuredRequest.Builder<String, Object, Map<String, Object>>(
            key,
            data,
            "$.field1[2:4]"
        ).build();
        var encrypted = client.encryptStructured(request);
        assertNotNull(encrypted);
        assertEquals(key, encrypted.getResult().getId());
        assertEquals(4, ((ArrayList<String>)encrypted.getResult().getStructuredData().get("field1")).size());
        assertEquals("data", encrypted.getResult().getStructuredData().get("field2"));

        // Decrypt what we encrypted.
        request = new EncryptStructuredRequest.Builder<String, Object, Map<String, Object>>(
            key,
            encrypted.getResult().getStructuredData(),
            "$.field1[2:4]"
        ).build();
        var decrypted = client.decryptStructured(request);
        assertNotNull(decrypted);
        assertEquals(key, decrypted.getResult().getId());
        assertEquals(4, ((ArrayList<String>)decrypted.getResult().getStructuredData().get("field1")).size());
        assertEquals("data", decrypted.getResult().getStructuredData().get("field2"));
    }
}
