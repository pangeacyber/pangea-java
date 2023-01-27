package cloud.pangeacyber.pangea.vault;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.vault.models.AsymmetricGenerateRequest;
import cloud.pangeacyber.pangea.vault.models.AsymmetricStoreRequest;
import cloud.pangeacyber.pangea.vault.models.DecryptRequest;
import cloud.pangeacyber.pangea.vault.models.DeleteRequest;
import cloud.pangeacyber.pangea.vault.models.EncryptRequest;
import cloud.pangeacyber.pangea.vault.models.GetRequest;
import cloud.pangeacyber.pangea.vault.models.KeyRotateRequest;
import cloud.pangeacyber.pangea.vault.models.ListRequest;
import cloud.pangeacyber.pangea.vault.models.RevokeRequest;
import cloud.pangeacyber.pangea.vault.models.SecretRotateRequest;
import cloud.pangeacyber.pangea.vault.models.SecretStoreRequest;
import cloud.pangeacyber.pangea.vault.models.SignRequest;
import cloud.pangeacyber.pangea.vault.models.SymmetricGenerateRequest;
import cloud.pangeacyber.pangea.vault.models.SymmetricStoreRequest;
import cloud.pangeacyber.pangea.vault.models.VerifyRequest;


public class VaultClient extends Client {
    public static String serviceName = "vault";

    public VaultClient(Config config) {
        super(config, serviceName);
    }

    /**
     * Revoke
     * @pangea.description Revoke an item
     * @param id - item id to revoke
     * @return RevokeResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public RevokeResponse revoke(String id) throws PangeaException, PangeaAPIException{
        return doPost("/v1/revoke", new RevokeRequest(id) , RevokeResponse.class);
    }

    /**
     * Delete
     * @pangea.description Delete an item
     * @param item id to delete
     * @return DeleteResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public DeleteResponse delete(String id) throws PangeaException, PangeaAPIException{
        return doPost("/v1/delete", new DeleteRequest(id) , DeleteResponse.class);
    }

    /**
     * Get
     * @pangea.description Get an item of any type
     * @param id - item id to get
     * @return GetResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public GetResponse get(String id) throws PangeaException, PangeaAPIException{
        return doPost("/v1/get", new GetRequest(id) , GetResponse.class);
    }

    /**
     * Get
     * @pangea.description Get an item of any type
     * @param id - item id to get
     * @param version - item version to get
     * @param verbose - true to get detailed item information
     * @return GetResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public GetResponse get(String id, Integer version, Boolean verbose) throws PangeaException, PangeaAPIException{
        return doPost("/v1/get", new GetRequest(id, version, verbose) , GetResponse.class);
    }

    /**
     * List
     * @pangea.description Retrieve a list of items
     * @param request - request parameters to send to list endpoint
     * @return ListResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public ListResponse get(ListRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/list", request , ListResponse.class);
    }

    /**
     * Update
     * @pangea.description Update the metadata of an item
     * @param request - request parameters to send to update endpoint
     * @return UpdateResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public UpdateResponse update(ListRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/update", request , UpdateResponse.class);
    }

    /**
     * Store a secret
     * @pangea.description Store a secret in vault service
     * @param request - request parameters to send to /secret/store endpoint
     * @return SecretStoreResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public SecretStoreResponse secretStore(SecretStoreRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/secret/store", request , SecretStoreResponse.class);
    }

    /**
     * Rotate a secret
     * @pangea.description Rotate a secret in vault service
     * @param id - secret id to rotate
     * @param secret - new secret value
     * @return SecretStoreResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public SecretRotateResponse secretRotate(String id, String secret) throws PangeaException, PangeaAPIException{
        return doPost("/v1/secret/rotate", new SecretRotateRequest(id, secret) , SecretRotateResponse.class);
    }

    /**
     * Generate a symmetric key
     * @pangea.description Generate a symmetric key in vault service
     * @param request - request parameters to send to /key/generate endpoint
     * @return SymmetricGenerateResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public SymmetricGenerateResponse symmetricGenerate(SymmetricGenerateRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/generate", request , SymmetricGenerateResponse.class);
    }

    /**
     * Generate an asymmetric key
     * @pangea.description Generate an asymmetric key in vault service
     * @param request - request parameters to send to /key/generate endpoint
     * @return AsymmetricGenerateResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public AsymmetricGenerateResponse asymmetricGenerate(AsymmetricGenerateRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/generate", request , AsymmetricGenerateResponse.class);
    }

    /**
     * Store an asymmetric key
     * @pangea.description Store an asymmetric key in vault service
     * @param request - request parameters to send to /key/store endpoint
     * @return AsymmetricStoreResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public AsymmetricStoreResponse asymmetricStore(AsymmetricStoreRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/store", request , AsymmetricStoreResponse.class);
    }

    /**
     * Store a symmetric key
     * @pangea.description Store a symmetric key in vault service
     * @param request - request parameters to send to /key/store endpoint
     * @return SymmetricStoreResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public SymmetricStoreResponse symmetricStore(SymmetricStoreRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/store", request , SymmetricStoreResponse.class);
    }

    /**
     * Rotate a key
     * @pangea.description Rotate a key
     * @param request - request parameters to send to /key/rotate endpoint
     * @return KeyRotateResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public KeyRotateResponse keyRotate(KeyRotateRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/rotate", request , KeyRotateResponse.class);
    }

    /**
     * Encrypt
     * @pangea.description Encrypt a message
     * @param id - key id to encrypt message
     * @param plainText - message to encrypt
     * @return EncryptResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public EncryptResponse encrypt(String id, String plainText) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/encrypt", new EncryptRequest(id, plainText) , EncryptResponse.class);
    }

    /**
     * Decrypt
     * @pangea.description Decrypt a message
     * @param id - key id to encrypt message
     * @param cipherText - message to decrypt
     * @return DecryptResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public DecryptResponse decrypt(String id, String cipherText) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/decrypt", new DecryptRequest(id, cipherText) , DecryptResponse.class);
    }

    /**
     * Decrypt
     * @pangea.description Decrypt a message
     * @param id - key id to encrypt message
     * @param cipherText - message to decrypt
     * @param version - key version to use on decryption
     * @return DecryptResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public DecryptResponse decrypt(String id, String cipherText, Integer version) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/decrypt", new DecryptRequest(id, cipherText, version) , DecryptResponse.class);
    }

    /**
     * Sign
     * @pangea.description sign a message
     * @param id - key id to sign message
     * @param message - message to sign
     * @return SignResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public SignResponse sign(String id, String message) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/sign", new SignRequest(id, message) , SignResponse.class);
    }

    /**
     * Verify
     * @pangea.description Verify a message/signature pair
     * @param id - key id to verify message/signature
     * @param message - message to verify
     * @param signature - signature to verify
     * @return VerifyResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public VerifyResponse verify(String id, String message, String signature) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/verify", new VerifyRequest(id, message, signature) , VerifyResponse.class);
    }

        /**
     * Verify
     * @pangea.description Verify a message/signature pair
     * @param id - key id to verify message/signature
     * @param message - message to verify
     * @param signature - signature to verify
     * @param version - key version to use on verification
     * @return VerifyResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public VerifyResponse verify(String id, String message, String signature, Integer version) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/verify", new VerifyRequest(id, message, signature, version) , VerifyResponse.class);
    }


}
