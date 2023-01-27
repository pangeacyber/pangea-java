package cloud.pangeacyber.pangea.vault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;
import cloud.pangeacyber.pangea.vault.models.AsymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.AsymmetricGenerateRequest;
import cloud.pangeacyber.pangea.vault.models.AsymmetricPurpose;
import cloud.pangeacyber.pangea.vault.models.AsymmetricStoreRequest;
import cloud.pangeacyber.pangea.vault.models.KeyRotateRequest;
import cloud.pangeacyber.pangea.vault.models.SecretStoreRequest;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.SymmetricGenerateRequest;
import cloud.pangeacyber.pangea.vault.models.SymmetricStoreRequest;


public class ITVaultTest
{
    VaultClient client;
    TestEnvironment environment = TestEnvironment.DEVELOP;

    @Before
    public void setUp() throws ConfigException{
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
        KeyRotateResponse rotateResponse = client.keyRotate(new KeyRotateRequest(id));
        assertEquals(Integer.valueOf(2), rotateResponse.getResult().getVersion());
        assertEquals(id, rotateResponse.getResult().getId());

        // Encrypt 2
        EncryptResponse encryptResponse2 = client.encrypt(id, dataB64);
        assertEquals(id, encryptResponse1.getResult().getId());
        assertEquals(Integer.valueOf(2), encryptResponse2.getResult().getVersion());
        assertNotNull(encryptResponse2.getResult().getCipherText());

        // Decrypt 1
        DecryptResponse decryptResponse1 = client.decrypt(id, encryptResponse1.getResult().getCipherText(),1);
        assertEquals(dataB64, decryptResponse1.getResult().getPlainText());

        // Decrypt 2
        DecryptResponse decryptResponse2 = client.decrypt(id, encryptResponse2.getResult().getCipherText(),2);
        assertEquals(dataB64, decryptResponse2.getResult().getPlainText());

        // Decrypt default
        DecryptResponse decryptResponseDefault = client.decrypt(id, encryptResponse2.getResult().getCipherText());
        assertEquals(dataB64, decryptResponseDefault.getResult().getPlainText());

        // Add faliures cases. Wrong ID, wrong version, etc


        // Revoke key
        RevokeResponse revokeResponse = client.revoke(id);
        assertEquals(id, revokeResponse.getResult().getId());

        // Decrypt after revoke
        DecryptResponse decryptResponseAfterRevoke = client.decrypt(id, encryptResponse1.getResult().getCipherText(), 1);
        assertEquals(dataB64, decryptResponseAfterRevoke.getResult().getPlainText());

    }

    private void signingCycle(String id) throws PangeaException, PangeaException, PangeaAPIException {
        String data = "thisisamessagetosign";

        // Sign 1
        SignResponse signResponse1 = client.sign(id, data);
        assertEquals(data, signResponse1.getResult().getId());
        assertEquals(Integer.valueOf(1), signResponse1.getResult().getVersion());
        assertNotNull(signResponse1.getResult().getSignature());

        // Rotate
        KeyRotateResponse rotateResponse = client.keyRotate(new KeyRotateRequest(id));
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


    @Test
    public void testAESSigningLifeCycle() throws PangeaException, PangeaAPIException{
        try{
            SymmetricGenerateRequest generateRequest = new SymmetricGenerateRequest(
                SymmetricAlgorithm.AES,
                false,
                false
            );
            generateRequest.setStore(false);

            // Generate without store
            SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
            assertNotNull(generateResp.getResult().getEncodedSymmetricKey());
            assertNull(generateResp.getResult().getId());
            SymmetricStoreResponse storeResponse = client.symmetricStore(new SymmetricStoreRequest(
                SymmetricAlgorithm.AES,
                generateResp.getResult().getEncodedSymmetricKey(),
                false
                ));
            assertNotNull(storeResponse.getResult().getId());
            encryptingCycle(storeResponse.getResult().getId());
        } catch(PangeaAPIException e){
            System.out.println(e.toString());
            assertTrue(false);
        }
    }

    @Test
    public void testSecretLifeCycle() throws PangeaException, PangeaAPIException{
        String secretV1 = "mysecret";
        String secretV2 = "mynewsecret";

        SecretStoreResponse storeResponse = client.secretStore(
            new SecretStoreRequest(secretV1, false)
        );
        assertEquals(secretV1, storeResponse.getResult().getSecret());
        assertEquals(Integer.valueOf(1), storeResponse.getResult().getVersion());
        assertNotNull(storeResponse.getResult().getId());

        SecretRotateResponse rotateResponse = client.secretRotate(
            storeResponse.getResult().getId(), secretV2);

        assertEquals(secretV2, rotateResponse.getResult().getSecret());
        assertEquals(Integer.valueOf(2), rotateResponse.getResult().getVersion());
        assertEquals(storeResponse.getResult().getId(), rotateResponse.getResult().getId());

        GetResponse getResponse = client.get(storeResponse.getResult().getId());
        assertEquals(secretV2, getResponse.getResult().getSecret());

        RevokeResponse revokeResponse = client.revoke(storeResponse.getResult().getId());
        assertEquals(storeResponse.getResult().getId(), revokeResponse.getResult().getId());

        try{
            GetResponse getReponse2 = client.get(storeResponse.getResult().getId());
            assertTrue(false);
        } catch (PangeaAPIException e){
            assertTrue(true);
        }
    }
}
