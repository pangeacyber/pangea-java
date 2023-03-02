package cloud.pangeacyber.pangea.vault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.vault.models.AsymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.responses.*;
import org.junit.Before;
import org.junit.Test;

public class ITVaultTest {

	VaultClient client;
	TestEnvironment environment = TestEnvironment.DEVELOP;

	@Before
	public void setUp() throws ConfigException {
		client = new VaultClient(Config.fromIntegrationEnvironment(environment));
	}

	private void encryptingCycle(String id) throws PangeaException, PangeaException, PangeaAPIException {
		String message = "thisisamessagetoencrypt";
		String dataB64 = Utils.stringToStringB64(message);

		// Encrypt 1
		EncryptResponse encryptResponse1 = client.encrypt(id, dataB64);
		assertEquals(id, encryptResponse1.getResult().getId());
		assertEquals(Integer.valueOf(1), encryptResponse1.getResult().getVersion());
		assertNotNull(encryptResponse1.getResult().getCipherText());

		// Rotate
		KeyRotateResponse rotateResponse = client.keyRotate(new KeyRotateRequest.KeyRotateRequestBuilder(id).build());
		assertEquals(Integer.valueOf(2), rotateResponse.getResult().getVersion());
		assertEquals(id, rotateResponse.getResult().getId());

		// Encrypt 2
		EncryptResponse encryptResponse2 = client.encrypt(id, dataB64);
		assertEquals(id, encryptResponse1.getResult().getId());
		assertEquals(Integer.valueOf(2), encryptResponse2.getResult().getVersion());
		assertNotNull(encryptResponse2.getResult().getCipherText());

		// Decrypt 1
		DecryptResponse decryptResponse1 = client.decrypt(id, encryptResponse1.getResult().getCipherText(), 1);
		assertEquals(dataB64, decryptResponse1.getResult().getPlainText());

		// Decrypt 2
		DecryptResponse decryptResponse2 = client.decrypt(id, encryptResponse2.getResult().getCipherText(), 2);
		assertEquals(dataB64, decryptResponse2.getResult().getPlainText());

		// Decrypt default
		DecryptResponse decryptResponseDefault = client.decrypt(id, encryptResponse2.getResult().getCipherText());
		assertEquals(dataB64, decryptResponseDefault.getResult().getPlainText());

		// Add faliures cases. Wrong ID, wrong version, etc

		// Revoke key
		RevokeResponse revokeResponse = client.revoke(id);
		assertEquals(id, revokeResponse.getResult().getId());

		// Decrypt after revoke
		DecryptResponse decryptResponseAfterRevoke = client.decrypt(
			id,
			encryptResponse1.getResult().getCipherText(),
			1
		);
		assertEquals(dataB64, decryptResponseAfterRevoke.getResult().getPlainText());
	}

	private void asymSigningCycle(String id) throws PangeaException, PangeaException, PangeaAPIException {
		String data = "thisisamessagetosign";

		// Sign 1
		SignResponse signResponse1 = client.sign(id, data);
		assertEquals(Integer.valueOf(1), signResponse1.getResult().getVersion());
		assertNotNull(signResponse1.getResult().getSignature());

		// Rotate
		KeyRotateResponse rotateResponse = client.keyRotate(new KeyRotateRequest.KeyRotateRequestBuilder(id).build());
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

		// Revoke key
		RevokeResponse revokeResponse = client.revoke(id);
		assertEquals(id, revokeResponse.getResult().getId());

		// Verify after revoke
		VerifyResponse verifyResponseAfterRevoke = client.verify(id, data, signResponse1.getResult().getSignature(), 1);
		assertEquals(Integer.valueOf(1), verifyResponseAfterRevoke.getResult().getVersion());
		assertTrue(verifyResponseAfterRevoke.getResult().isValidSignature());
	}

	private void jwtSigningCycle(String id) throws PangeaException, PangeaException, PangeaAPIException {
		String payload = """
            {'message': 'message to sign', 'data': 'Some extra data'}
            """;

		// Sign 1
		JWTSignResponse signResponse1 = client.jwtSign(id, payload);
		assertNotNull(signResponse1.getResult().getJws());

		// Rotate
		KeyRotateResponse rotateResponse = client.keyRotate(new KeyRotateRequest.KeyRotateRequestBuilder(id).build());
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
		assertEquals(1, getResponse.getResult().getJWK().getKeys().length);

		// Gets all
		getResponse = client.jwkGet(id, "all");
		assertEquals(2, getResponse.getResult().getJWK().getKeys().length);

		// Gets -1
		getResponse = client.jwkGet(id, "-1");
		assertEquals(2, getResponse.getResult().getJWK().getKeys().length);

		// Revoke key
		RevokeResponse revokeResponse = client.revoke(id);
		assertEquals(id, revokeResponse.getResult().getId());

		// Verify after revoke
		JWTVerifyResponse verifyResponseAfterRevoke = client.jwtVerify(signResponse1.getResult().getJws());
		assertTrue(verifyResponseAfterRevoke.getResult().isValidSignature());
	}

	@Test
	public void testAESEncryptingLifeCycle() throws PangeaException, PangeaAPIException {
		try {
			SymmetricGenerateRequest generateRequest = new SymmetricGenerateRequest.SymmetricGenerateRequestBuilder()
				.setAlgorithm(SymmetricAlgorithm.AES)
				.setStore(false)
				.setManaged(false)
				.build();

			// Generate without store
			SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
			assertNotNull(generateResp.getResult().getEncodedSymmetricKey());
			assertNull(generateResp.getResult().getId());
			SymmetricStoreResponse storeResponse = client.symmetricStore(
				new SymmetricStoreRequest.SymmetricStoreRequestBuilder(
					SymmetricAlgorithm.AES,
					generateResp.getResult().getEncodedSymmetricKey()
				)
					.build()
			);
			assertNotNull(storeResponse.getResult().getId());
			encryptingCycle(storeResponse.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testEd25519SigningLifeCycle() throws PangeaException, PangeaAPIException {
		try {
			AsymmetricGenerateRequest generateRequest = new AsymmetricGenerateRequest.AsymmetricGenerateRequestBuilder()
				.setAlgorithm(AsymmetricAlgorithm.ED25519)
				.setStore(false)
				.setManaged(false)
				.build();

			// Generate without store
			AsymmetricGenerateResponse generateResp = client.asymmetricGenerate(generateRequest);
			assertNotNull(generateResp.getResult().getEncodedPrivateKey());
			assertNotNull(generateResp.getResult().getEncodedPrivateKey());
			assertNull(generateResp.getResult().getId());
			AsymmetricStoreResponse storeResponse = client.asymmetricStore(
				new AsymmetricStoreRequest.AsymmetricStoreRequestBuilder(
					AsymmetricAlgorithm.ED25519,
					generateResp.getResult().getEncodedPublicKey(),
					generateResp.getResult().getEncodedPrivateKey()
				)
					.build()
			);
			assertNotNull(storeResponse.getResult().getId());
			asymSigningCycle(storeResponse.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testJWTasymES256SigningLifeCycle() throws PangeaException, PangeaAPIException {
		KeyPurpose purpose = KeyPurpose.JWT;
		AsymmetricAlgorithm algorithm = AsymmetricAlgorithm.ES256;
		try {
			AsymmetricGenerateRequest generateRequest = new AsymmetricGenerateRequest.AsymmetricGenerateRequestBuilder()
				.setAlgorithm(algorithm)
				.setPurpose(purpose)
				.setStore(false)
				.setManaged(false)
				.build();

			// Generate without store
			AsymmetricGenerateResponse generateResp = client.asymmetricGenerate(generateRequest);
			assertNotNull(generateResp.getResult().getEncodedPrivateKey());
			assertNotNull(generateResp.getResult().getEncodedPrivateKey());
			assertNull(generateResp.getResult().getId());
			AsymmetricStoreResponse storeResponse = client.asymmetricStore(
				new AsymmetricStoreRequest.AsymmetricStoreRequestBuilder(
					algorithm,
					generateResp.getResult().getEncodedPublicKey(),
					generateResp.getResult().getEncodedPrivateKey()
				)
					.setPurpose(purpose)
					.build()
			);
			assertNotNull(storeResponse.getResult().getId());
			jwtSigningCycle(storeResponse.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}

	@Test
	public void testJWTsymHS256SigningLifeCycle() throws PangeaException, PangeaAPIException {
		KeyPurpose purpose = KeyPurpose.JWT;
		SymmetricAlgorithm algorithm = SymmetricAlgorithm.HS256;
		try {
			SymmetricGenerateRequest generateRequest = new SymmetricGenerateRequest.SymmetricGenerateRequestBuilder()
				.setAlgorithm(algorithm)
				.setPurpose(purpose)
				.setStore(false)
				.setManaged(false)
				.build();

			// Generate without store
			SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
			assertNotNull(generateResp.getResult().getEncodedSymmetricKey());
			assertNull(generateResp.getResult().getId());
			SymmetricStoreResponse storeResponse = client.symmetricStore(
				new SymmetricStoreRequest.SymmetricStoreRequestBuilder(
					algorithm,
					generateResp.getResult().getEncodedSymmetricKey()
				)
					.setPurpose(purpose)
					.build()
			);
			assertNotNull(storeResponse.getResult().getId());
			jwtSigningCycle(storeResponse.getResult().getId());
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
		try {
			storeResponse = client.secretStore(new SecretStoreRequest.SecretStoreRequestBuilder(secretV1).build());
			assertEquals(secretV1, storeResponse.getResult().getSecret());
			assertEquals(Integer.valueOf(1), storeResponse.getResult().getVersion());
			assertNotNull(storeResponse.getResult().getId());

			SecretRotateResponse rotateResponse = client.secretRotate(storeResponse.getResult().getId(), secretV2);

			assertEquals(secretV2, rotateResponse.getResult().getSecret());
			assertEquals(Integer.valueOf(2), rotateResponse.getResult().getVersion());
			assertEquals(storeResponse.getResult().getId(), rotateResponse.getResult().getId());

			GetResponse getResponse = client.get(storeResponse.getResult().getId());
			assertEquals(secretV2, getResponse.getResult().getSecret());

			RevokeResponse revokeResponse = client.revoke(storeResponse.getResult().getId());
			assertEquals(storeResponse.getResult().getId(), revokeResponse.getResult().getId());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
			assertTrue(false);
		}

		GetResponse getReponse2 = client.get(storeResponse.getResult().getId());
		assertNotNull(getReponse2.getResult().getSecret());
	}
}
