package cloud.pangeacyber.pangea.vault;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.CryptoUtils;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.vault.models.AsymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.ExportEncryptionAlgorithm;
import cloud.pangeacyber.pangea.vault.models.ExportEncryptionType;
import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.ItemVersionState;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.TransformAlphabet;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.responses.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ITVaultTest {

	VaultClient client;
	static TestEnvironment environment;
	String time;
	Random random;
	final static public String actor = "JavaSDKTest";

	@BeforeAll
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("vault", TestEnvironment.LIVE);
	}

	@BeforeEach
	public void setUp() throws Exception {
		client = new VaultClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		time = dtf.format(LocalDateTime.now());
		this.random = new Random();
	}

	@AfterAll
	public static void tearDownClass() throws PangeaException, PangeaAPIException, ConfigException {
		VaultClient client = new VaultClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
		int item_counter = 0;
		int list_call_counter = 0;
		String last = null;
		long time_start = System.currentTimeMillis();

		while (item_counter < 500) {
			// List
			Map<String, String> filter = new LinkedHashMap<String, String>(1);
			filter.put("name__contains", ITVaultTest.actor);

			final var listResp = client.list(ListRequest.builder().filter(filter).last(last).build());
			list_call_counter++;
			System.out.printf("List call %d\n", list_call_counter);

			last = listResp.getResult().getLast();

			for (var item : listResp.getResult().getItems()) {
				// Delete
				client.delete(item.getId());
				item_counter++;
			}

			if (listResp.getResult().getItems().size() == 0) {
				break;
			}
		}
		long time_end = System.currentTimeMillis();
		System.out.printf("Deleted %d items in %d seconds\n", item_counter, (time_end - time_start)/1000);
		System.out.printf("Average delete time: %f ms\n", ((time_end - time_start) / (double) item_counter));
	}

	private String getRandomID() {
		return String.valueOf(this.random.nextInt(1000000));
	}

	private String getName() {
		String callerName = new Throwable().getStackTrace()[1].getMethodName();
		return String.format("%s_%s_%s_%s", this.time, actor, callerName, getRandomID());
	}

	private void encryptingCycle(String id) throws PangeaException, PangeaAPIException {
		String message = "thisisamessagetoencrypt";
		String dataB64 = Utils.stringToStringB64(message);

		// Encrypt 1
		final var encryptResponse1 = client.encrypt(EncryptRequest.builder().id(id).plainText(dataB64).build());
		assertEquals(id, encryptResponse1.getResult().getId());
		assertEquals(Integer.valueOf(1), encryptResponse1.getResult().getVersion());
		assertNotNull(encryptResponse1.getResult().getCipherText());

		// Rotate
		final var rotateResponse = client.keyRotate(
				KeyRotateRequest.builder().id(id).rotationState(ItemVersionState.SUSPENDED).build());
		assertEquals(Integer.valueOf(2), rotateResponse.getResult().getItemVersions().get(0).getVersion());
		assertEquals(id, rotateResponse.getResult().getId());

		// Encrypt 2
		final var encryptResponse2 = client.encrypt(
				EncryptRequest.builder().id(id).plainText(dataB64).version(2).build());
		assertEquals(id, encryptResponse1.getResult().getId());
		assertEquals(Integer.valueOf(2), encryptResponse2.getResult().getVersion());
		assertNotNull(encryptResponse2.getResult().getCipherText());

		// Decrypt 1
		DecryptResponse decryptResponse1 = client.decrypt(
				DecryptRequest.builder().id(id).cipherText(encryptResponse1.getResult().getCipherText()).version(1)
						.build());
		assertEquals(dataB64, decryptResponse1.getResult().getPlainText());

		// Decrypt 2
		DecryptResponse decryptResponse2 = client.decrypt(
				DecryptRequest.builder().id(id).cipherText(encryptResponse2.getResult().getCipherText()).version(2)
						.build());
		assertEquals(dataB64, decryptResponse2.getResult().getPlainText());

		// Decrypt default
		DecryptResponse decryptResponseDefault = client.decrypt(
				DecryptRequest.builder().id(id).cipherText(encryptResponse2.getResult().getCipherText()).build());
		assertEquals(dataB64, decryptResponseDefault.getResult().getPlainText());

		// Add failure cases. Wrong ID, wrong version, etc

		// Suspend key
		StateChangeResponse stateChangeResponse = client.stateChange(id, ItemVersionState.DEACTIVATED, 1, null);
		assertEquals(id, stateChangeResponse.getResult().getId());

		// Decrypt after revoke
		DecryptResponse decryptResponseAfterSuspend = client.decrypt(
				DecryptRequest.builder().id(id).cipherText(encryptResponse1.getResult().getCipherText()).version(1)
						.build());
		assertEquals(dataB64, decryptResponseAfterSuspend.getResult().getPlainText());
	}

	private void encryptingCycleFPE(String id) throws PangeaException, PangeaAPIException {
		String plainText = "123-4567-8901";
		String tweak = "MTIzMTIzMT==";

		// Encrypt 1
		final var encryptResponse1 = client.encryptTransform(
				EncryptTransformRequest
						.builder()
						.id(id)
						.plainText(plainText)
						.alphabet(TransformAlphabet.ALPHANUMERIC)
						.tweak(tweak)
						.build());
		assertEquals(id, encryptResponse1.getResult().getId());
		assertEquals(1, encryptResponse1.getResult().getVersion());
		assertNotNull(encryptResponse1.getResult().getCipherText());
		assertEquals(encryptResponse1.getResult().getCipherText().length(), plainText.length());

		// Decrypt 1
		DecryptTransformResponse decryptResponse1 = client.decryptTransform(
				DecryptTransformRequest
						.builder()
						.id(id)
						.cipherText(encryptResponse1.getResult().getCipherText())
						.tweak(tweak)
						.alphabet(TransformAlphabet.ALPHANUMERIC)
						.version(1)
						.build());
		assertEquals(plainText, decryptResponse1.getResult().getPlainText());
	}

	private void asymSigningCycle(String id) throws PangeaException, PangeaAPIException {
		String message = "thisisamessagetosign";
		String data = Utils.stringToStringB64(message);

		// Sign 1
		SignResponse signResponse1 = client.sign(id, data);
		assertEquals(Integer.valueOf(1), signResponse1.getResult().getVersion());
		assertNotNull(signResponse1.getResult().getSignature());

		// Rotate
		final var rotateResponse = client.keyRotate(
				KeyRotateRequest.builder().id(id).rotationState(ItemVersionState.SUSPENDED).build());
		assertEquals(Integer.valueOf(2), rotateResponse.getResult().getItemVersions().get(0).getVersion());

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
		assertEquals(2, verifyResponse2.getResult().getVersion());
		assertTrue(verifyResponse2.getResult().isValidSignature());

		// TODO: Add failures cases

		// Wrong signature. Use signature of version 1, and try to verify with
		// default version (2)
		VerifyResponse verifyResponseBad = client.verify(id, data, signResponse1.getResult().getSignature());
		assertFalse(verifyResponseBad.getResult().isValidSignature());

		// Suspend key
		StateChangeResponse stateChangeResponse = client.stateChange(id, ItemVersionState.DEACTIVATED, 1, null);
		assertEquals(id, stateChangeResponse.getResult().getId());

		// Verify after revoke
		VerifyResponse verifyResponseAfterSuspend = client.verify(
				id,
				data,
				signResponse1.getResult().getSignature(),
				1);
		assertEquals(Integer.valueOf(1), verifyResponseAfterSuspend.getResult().getVersion());
		assertTrue(verifyResponseAfterSuspend.getResult().isValidSignature());
	}

	private void jwtAsymSigningCycle(String id) throws PangeaException, PangeaAPIException {
		String payload = "{'message': 'message to sign', 'data': 'Some extra data'}";

		// Sign 1
		JWTSignResponse signResponse1 = client.jwtSign(id, payload);
		assertNotNull(signResponse1.getResult().getJws());

		// Rotate
		final var rotateResponse = client.keyRotate(
				KeyRotateRequest.builder().id(id).rotationState(ItemVersionState.SUSPENDED).build());
		assertEquals(Integer.valueOf(2), rotateResponse.getResult().getItemVersions().get(0).getVersion());

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
		assertEquals(1, getResponse.getResult().getKeys().size());

		// Gets all
		getResponse = client.jwkGet(id, "all");
		assertEquals(2, getResponse.getResult().getKeys().size());

		// Gets -1
		getResponse = client.jwkGet(id, "-1");
		assertEquals(1, getResponse.getResult().getKeys().size());

		// Suspend key
		final var stateChangeResponse = client.stateChange(id, ItemVersionState.DEACTIVATED, 1, null);
		assertEquals(id, stateChangeResponse.getResult().getId());

		// Verify after revoke
		JWTVerifyResponse verifyResponseAfterSuspend = client.jwtVerify(signResponse1.getResult().getJws());
		assertTrue(verifyResponseAfterSuspend.getResult().isValidSignature());
	}

	private void jwtSymSigningCycle(String id) throws PangeaException, PangeaAPIException {
		String payload = "{'message': 'message to sign', 'data': 'Some extra data'}";

		// Sign 1
		JWTSignResponse signResponse1 = client.jwtSign(id, payload);
		assertNotNull(signResponse1.getResult().getJws());

		// Rotate
		final var rotateResponse = client.keyRotate(
				KeyRotateRequest.builder().id(id).rotationState(ItemVersionState.SUSPENDED).build());
		assertEquals(Integer.valueOf(2), rotateResponse.getResult().getItemVersions().get(0).getVersion());

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
		final var stateChangeResponse = client.stateChange(id, ItemVersionState.DEACTIVATED, 1, null);
		assertEquals(id, stateChangeResponse.getResult().getId());

		// Verify after revoke
		JWTVerifyResponse verifyResponseAfterSuspend = client.jwtVerify(signResponse1.getResult().getJws());
		assertTrue(verifyResponseAfterSuspend.getResult().isValidSignature());
	}

	@Test
	public void testSymmetricEncryptionGenerate() throws PangeaException, PangeaAPIException {
		SymmetricAlgorithm[] algorithms = new SymmetricAlgorithm[] {
				SymmetricAlgorithm.AES128_CBC,
				SymmetricAlgorithm.AES256_CBC,
				SymmetricAlgorithm.AES128_CFB,
				SymmetricAlgorithm.AES256_CFB,
				SymmetricAlgorithm.AES256_GCM,
		};
		boolean failed = false;
		KeyPurpose purpose = KeyPurpose.ENCRYPTION;

		for (SymmetricAlgorithm algorithm : algorithms) {
			String name = getName();
			try {
				final var generateRequest = SymmetricGenerateRequest
						.builder()
						.algorithm(algorithm)
						.purpose(purpose)
						.name(name)
						.build();

				client.symmetricGenerate(generateRequest);
			} catch (PangeaAPIException e) {
				System.out.printf("Failed to generate %s %s\n%s\n\n", algorithms, purpose, e.toString());
				failed = true;
			}
		}
		assertFalse(failed);
	}

	@Test
	public void testAsymmetricSigningGenerate() throws PangeaException, PangeaAPIException {
		AsymmetricAlgorithm[] algorithms = new AsymmetricAlgorithm[] {
				AsymmetricAlgorithm.ED25519,
				AsymmetricAlgorithm.RSA2048_PKCS1V15_SHA256,
				AsymmetricAlgorithm.ES256K,
				AsymmetricAlgorithm.RSA2048_PSS_SHA256,
				AsymmetricAlgorithm.RSA3072_PSS_SHA256,
				AsymmetricAlgorithm.RSA4096_PSS_SHA256,
				AsymmetricAlgorithm.RSA4096_PSS_SHA512,
				AsymmetricAlgorithm.Ed25519_DILITHIUM2_BETA,
				AsymmetricAlgorithm.Ed448_DILITHIUM3_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_128F_SHAKE256_SIMPLE_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_128F_SHAKE256_ROBUST_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_192F_SHAKE256_SIMPLE_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_192F_SHAKE256_ROBUST_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_256F_SHAKE256_SIMPLE_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_256F_SHAKE256_ROBUST_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_128F_SHA256_SIMPLE_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_128F_SHA256_ROBUST_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_192F_SHA256_SIMPLE_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_192F_SHA256_ROBUST_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_256F_SHA256_SIMPLE_BETA,
				AsymmetricAlgorithm.SPHINCSPLUS_256F_SHA256_ROBUST_BETA,
				AsymmetricAlgorithm.FALCON_1024_BETA,
		};
		boolean failed = false;
		KeyPurpose purpose = KeyPurpose.SIGNING;

		for (AsymmetricAlgorithm algorithm : algorithms) {
			String name = getName();
			try {
				final var generateRequest = AsymmetricGenerateRequest
						.builder()
						.algorithm(algorithm)
						.purpose(purpose)
						.name(name)
						.build();

				// Generate
				client.asymmetricGenerate(generateRequest);
			} catch (PangeaAPIException e) {
				System.out.printf("Failed to generate %s %s\n%s\n\n", algorithms, purpose, e.toString());
				failed = true;
			}
		}
		assertFalse(failed);
	}

	@Test
	public void testAsymmetricEncryptionGenerate() throws PangeaException, PangeaAPIException {
		AsymmetricAlgorithm[] algorithms = new AsymmetricAlgorithm[] {
				AsymmetricAlgorithm.RSA2048_OAEP_SHA1,
				AsymmetricAlgorithm.RSA2048_OAEP_SHA512,
				AsymmetricAlgorithm.RSA3072_OAEP_SHA1,
				AsymmetricAlgorithm.RSA3072_OAEP_SHA256,
				AsymmetricAlgorithm.RSA3072_OAEP_SHA512,
				AsymmetricAlgorithm.RSA4096_OAEP_SHA1,
				AsymmetricAlgorithm.RSA4096_OAEP_SHA256,
				AsymmetricAlgorithm.RSA4096_OAEP_SHA512,
		};
		boolean failed = false;
		KeyPurpose purpose = KeyPurpose.ENCRYPTION;

		for (AsymmetricAlgorithm algorithm : algorithms) {
			String name = getName();
			try {
				final var generateRequest = AsymmetricGenerateRequest
						.builder()
						.algorithm(algorithm)
						.purpose(purpose)
						.name(name)
						.build();

				// Generate
				client.asymmetricGenerate(generateRequest);
			} catch (PangeaAPIException e) {
				System.out.printf("Failed to generate %s %s\n%s\n\n", algorithms, purpose, e.toString());
				failed = true;
			}
		}
		assertFalse(failed);
	}

	@Test
	public void testAESEncryptingLifeCycle() throws PangeaException, PangeaAPIException {
		SymmetricAlgorithm[] algorithms = new SymmetricAlgorithm[] {
				SymmetricAlgorithm.AES128_CBC,
				SymmetricAlgorithm.AES256_CBC,
				SymmetricAlgorithm.AES128_CFB,
				SymmetricAlgorithm.AES256_CFB,
				SymmetricAlgorithm.AES256_GCM,
		};

		for (SymmetricAlgorithm algorithm : algorithms) {
			String name = getName();
			try {
				final var generateRequest = SymmetricGenerateRequest
						.builder()
						.algorithm(algorithm)
						.purpose(KeyPurpose.ENCRYPTION)
						.name(name)
						.build();

				SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
				assertNotNull(generateResp.getResult().getId());
				assertEquals(1, generateResp.getResult().getItemVersions().get(0).getVersion());
				encryptingCycle(generateResp.getResult().getId());
			} catch (PangeaAPIException e) {
				System.out.printf("Failed testAESEncryptingLifeCycle with algorithm %s.\n", algorithm);
				System.out.println(e.toString());
				assertTrue(false);
			}
		}
	}

	@Test
	public void testFPEEncryptingLifeCycle() throws PangeaException, PangeaAPIException {
		SymmetricAlgorithm[] algorithms = new SymmetricAlgorithm[] {
				SymmetricAlgorithm.AES128_FF3_1,
				SymmetricAlgorithm.AES256_FF3_1,
		};

		for (SymmetricAlgorithm algorithm : algorithms) {
			String name = getName();
			try {
				final var generateRequest = SymmetricGenerateRequest
						.builder()
						.algorithm(algorithm)
						.purpose(KeyPurpose.FPE)
						.name(name)
						.build();

				SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
				assertNotNull(generateResp.getResult().getId());
				assertEquals(1, generateResp.getResult().getItemVersions().get(0).getVersion());
				encryptingCycleFPE(generateResp.getResult().getId());
			} catch (PangeaAPIException e) {
				System.out.printf("Failed testFPEEncryptingLifeCycle with algorithm %s.\n", algorithm);
				System.out.println(e.toString());
				assertTrue(false);
			}
		}
	}

	@Test
	public void testEd25519SigningLifeCycle() throws PangeaException, PangeaAPIException {
		String name = getName();
		try {
			final var generateRequest = AsymmetricGenerateRequest
					.builder()
					.algorithm(AsymmetricAlgorithm.ED25519)
					.purpose(KeyPurpose.SIGNING)
					.name(name)
					.build();

			// Generate
			AsymmetricGenerateResponse generateResp = client.asymmetricGenerate(generateRequest);
			assertNotNull(generateResp.getResult().getId());
			assertEquals(1, generateResp.getResult().getItemVersions().get(0).getVersion());
			asymSigningCycle(generateResp.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testJWTasymES256SigningLifeCycle() throws PangeaException, PangeaAPIException {
		KeyPurpose purpose = KeyPurpose.JWT;
		AsymmetricAlgorithm[] algorithms = new AsymmetricAlgorithm[] {
				AsymmetricAlgorithm.ES256,
				AsymmetricAlgorithm.ES384,
				AsymmetricAlgorithm.ES512,
		};

		for (AsymmetricAlgorithm algorithm : algorithms) {
			String name = getName();
			try {
				final var generateRequest = AsymmetricGenerateRequest
						.builder()
						.algorithm(algorithm)
						.purpose(purpose)
						.name(name)
						.build();

				// Generate
				AsymmetricGenerateResponse generateResp = client.asymmetricGenerate(generateRequest);
				assertNotNull(generateResp.getResult().getId());
				assertEquals(1, generateResp.getResult().getItemVersions().get(0).getVersion());
				jwtAsymSigningCycle(generateResp.getResult().getId());
			} catch (PangeaAPIException e) {
				System.out.printf("Failed testJWTasymES256SigningLifeCycle with algorithm %s.\n", algorithm);
				System.out.println(e.toString());
				assertTrue(false);
			}
		}
	}

	@Test
	public void testJWTsymHS256SigningLifeCycle() throws PangeaException, PangeaAPIException {
		KeyPurpose purpose = KeyPurpose.JWT;
		SymmetricAlgorithm[] algorithms = new SymmetricAlgorithm[] {
				SymmetricAlgorithm.HS256,
				SymmetricAlgorithm.HS384,
				SymmetricAlgorithm.HS512,
		};

		for (SymmetricAlgorithm algorithm : algorithms) {
			String name = getName();
			try {
				final var generateRequest = SymmetricGenerateRequest
						.builder()
						.algorithm(algorithm)
						.purpose(purpose)
						.name(name)
						.build();

				// Generate
				SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
				assertNotNull(generateResp.getResult().getId());
				assertEquals(1, generateResp.getResult().getItemVersions().get(0).getVersion());
				jwtSymSigningCycle(generateResp.getResult().getId());
			} catch (PangeaAPIException e) {
				System.out.printf("Failed testJWTsymHS256SigningLifeCycle with algorithm %s.\n", algorithm);
				System.out.println(e.toString());
				assertTrue(false);
			}
		}
	}

	@Test
	public void testSecretLifeCycle() throws PangeaException, PangeaAPIException {
		String secretV1 = "mysecret";
		String secretV2 = "mynewsecret";
		SecretStoreResponse storeResponse = null;
		String name = getName();
		try {
			storeResponse = client.secretStore(SecretStoreRequest.builder().secret(secretV1).name(name).build());
			assertEquals(1, storeResponse.getResult().getItemVersions().size());
			assertNotNull(storeResponse.getResult().getId());

			final var rotateResponse = client.secretRotate(
					SecretRotateRequest
							.builder()
							.id(storeResponse.getResult().getId())
							.secret(secretV2)
							.rotationState(ItemVersionState.SUSPENDED)
							.build());

			assertEquals(2, rotateResponse.getResult().getItemVersions().get(0).getVersion());
			assertEquals(storeResponse.getResult().getId(), rotateResponse.getResult().getId());

			final var getResponse = client.get(GetRequest.builder().id(storeResponse.getResult().getId()).build());
			assertNotNull(getResponse.getResult().getItemVersions());

			final var stateChangeResponse = client.stateChange(
					storeResponse.getResult().getId(),
					ItemVersionState.DEACTIVATED,
					1,
					null);
			assertEquals(storeResponse.getResult().getId(), stateChangeResponse.getResult().getId());
			assertEquals(
					ItemVersionState.DEACTIVATED,
					stateChangeResponse.getResult().getItemVersions().get(0).getState());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}

		final var getResponse2 = client.get(GetRequest.builder().id(storeResponse.getResult().getId()).build());
		assertNotNull(getResponse2.getResult().getItemVersions());
	}

	@Test
	public void testFolders() throws PangeaException, PangeaAPIException {
		final String FOLDER_PARENT = String.format("test_parent_folder_%s", this.time);
		final String FOLDER_NAME = "test_folder_name";
		final String FOLDER_NAME_NEW = "test_folder_name_new";

		try {
			// Create parent
			final var createParentResp = client.folderCreate(
					FolderCreateRequest.builder().name(FOLDER_PARENT).folder("/").build());
			String parentID = createParentResp.getResult().getId();
			assertNotNull(parentID);

			// Create folder
			final var createFolderResp = client.folderCreate(
					FolderCreateRequest.builder().name(FOLDER_NAME).folder(FOLDER_PARENT).build());
			String folderID = createFolderResp.getResult().getId();
			assertNotNull(folderID);

			// Update name
			final var updateResp = client.update(UpdateRequest.builder().id(folderID).name(FOLDER_NAME_NEW).build());
			assertEquals(folderID, updateResp.getResult().getId());

			// List
			Map<String, String> filter = new LinkedHashMap<String, String>(1);
			filter.put("folder", FOLDER_PARENT);

			final var listResp = client.list(ListRequest.builder().filter(filter).build());
			assertEquals(1, listResp.getResult().getItems().size());
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
	public void testEncryptStructured()
			throws JsonProcessingException, JsonMappingException, PangeaException, PangeaAPIException {
		// Test data.
		String payload = "{ \"field1\": [\"1\", \"2\", \"true\", \"false\"], \"field2\": \"data\" }";
		var data = new ObjectMapper().readValue(payload, new TypeReference<Map<String, Object>>() {
		});

		// Generate an encryption key.
		var generateRequest = SymmetricGenerateRequest
				.builder()
				.algorithm(SymmetricAlgorithm.AES256_CFB)
				.purpose(KeyPurpose.ENCRYPTION)
				.name(getName())
				.build();
		var generateResp = client.symmetricGenerate(generateRequest);
		var key = generateResp.getResult().getId();
		assertNotNull(key);

		// Encrypt.
		var request = new EncryptStructuredRequest.Builder<String, Object, Map<String, Object>>(
				key,
				data,
				"$.field1[2:4]")
				.build();
		var encrypted = client.encryptStructured(request);
		assertNotNull(encrypted);
		assertEquals(key, encrypted.getResult().getId());
		assertEquals(4, ((ArrayList<String>) encrypted.getResult().getStructuredData().get("field1")).size());
		assertEquals("data", encrypted.getResult().getStructuredData().get("field2"));

		// Decrypt what we encrypted.
		request = new EncryptStructuredRequest.Builder<String, Object, Map<String, Object>>(
				key,
				encrypted.getResult().getStructuredData(),
				"$.field1[2:4]")
				.build();
		var decrypted = client.decryptStructured(request);
		assertNotNull(decrypted);
		assertEquals(key, decrypted.getResult().getId());
		assertEquals(4, ((ArrayList<String>) decrypted.getResult().getStructuredData().get("field1")).size());
		assertEquals("data", decrypted.getResult().getStructuredData().get("field2"));
	}

	@Test
	void testExport()
			throws PangeaException, PangeaAPIException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
		// Generate an exportable key.
		final var generateRequest = AsymmetricGenerateRequest
				.builder()
				.algorithm(AsymmetricAlgorithm.ED25519)
				.purpose(KeyPurpose.SIGNING)
				.name(getName())
				.exportable(true)
				.build();
		final var generated = client.asymmetricGenerate(generateRequest);
		final var key = generated.getResult().getId();
		assertNotNull(key);

		final var filter = new HashMap<String, String>();
		filter.put("id", key);
		final var fetched = client.getBulk(GetBulkRequest.builder().filter(filter).build());
		final var expectedPublicKey = fetched.getResult().getItems().get(0).getItemVersions().get(0).getPublicKey();

		// Generate a RSA key pair.
		final var keyPair = CryptoUtils.generateRsaKeyPair(4096);
		final var publicKey = CryptoUtils.asymmetricPemExport(keyPair.getPublic());

		// Export it.
		var request = ExportRequest
				.builder()
				.id(key)
				.asymmetricAlgorithm(ExportEncryptionAlgorithm.RSA4096_OAEP_SHA512)
				.asymmetricPublicKey(publicKey)
				.build();
		var actual = client.export(request);
		assertNotNull(actual);
		assertEquals(key, actual.getResult().getId());
		assertEquals(ExportEncryptionType.ASYMMETRIC, actual.getResult().getEncryptionType());
		final var exportedPublicKey = actual.getResult().getPublicKey();

		assertEquals(expectedPublicKey, exportedPublicKey);
		final var decodedPrivateKey = (new Base64(true)).decode(actual.getResult().getPrivateKey());
		final var decryptedPrivateKey = CryptoUtils.asymmetricDecrypt(
				ExportEncryptionAlgorithm.RSA4096_OAEP_SHA512,
				keyPair.getPrivate(),
				decodedPrivateKey);

		// Store it under a new name, again as exportable.
		final var storeRequest = AsymmetricStoreRequest
				.builder()
				.privateKey(new String(decryptedPrivateKey))
				.publicKey(expectedPublicKey)
				.algorithm(AsymmetricAlgorithm.ED25519)
				.purpose(KeyPurpose.SIGNING)
				.name(getName())
				.exportable(true)
				.build();
		final var stored = client.asymmetricStore(storeRequest);
		final var storedKey = stored.getResult().getId();
		assertNotNull(storedKey);

		// Should still be able to export it.
		request = ExportRequest.builder().id(storedKey).build();
		actual = client.export(request);
		assertNotNull(actual);
		assertEquals(storedKey, actual.getResult().getId());
		assertNotNull(actual.getResult().getPublicKey());
		assertNotNull(actual.getResult().getPrivateKey());
	}

	@Test
	void testExportKem() throws PangeaException, PangeaAPIException {
		// Generate a RSA key pair.
		final var keyPair = CryptoUtils.generateRsaKeyPair(4096);
		final var publicKey = CryptoUtils.asymmetricPemExport(keyPair.getPublic());

		// Generate an exportable key.
		final var generateRequest = AsymmetricGenerateRequest
				.builder()
				.algorithm(AsymmetricAlgorithm.ED25519)
				.purpose(KeyPurpose.SIGNING)
				.name(getName())
				.exportable(true)
				.build();
		final var generated = client.asymmetricGenerate(generateRequest);
		final var key = generated.getResult().getId();
		assertNotNull(key);

		// Export without any encryption.
		final var plainExport = client.export(ExportRequest.builder().id(key).build()).getResult();
		assertNotNull(plainExport);
		assertEquals(key, plainExport.getId());
		assertEquals(ItemType.ASYMMETRIC_KEY, plainExport.getType());
		assertNotNull(plainExport.getPrivateKey());

		// Export with KEM.
		var request = ExportRequest
				.builder()
				.id(key)
				.asymmetricAlgorithm(ExportEncryptionAlgorithm.RSA_NO_PADDING_4096_KEM)
				.asymmetricPublicKey(publicKey)
				.kemPassword("password")
				.build();
		var actual = client.export(request).getResult();
		assertNotNull(actual);
		assertEquals(key, actual.getId());
		assertEquals(ExportEncryptionType.KEM, actual.getEncryptionType());
		assertEquals(plainExport.getPublicKey(), actual.getPublicKey());

		final var decryptedPrivateKey = CryptoUtils.kemDecrypt(actual, "password", keyPair.getPrivate());
		assertEquals(plainExport.getPrivateKey(), decryptedPrivateKey);
	}
}
