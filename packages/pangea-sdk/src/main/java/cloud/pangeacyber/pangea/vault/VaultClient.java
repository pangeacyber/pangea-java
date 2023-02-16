package cloud.pangeacyber.pangea.vault;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.requests.SecretRotateRequest.SecretRotateRequestBuilder;
import cloud.pangeacyber.pangea.vault.responses.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


final class GetRequest {
    @JsonProperty("id")
    String id;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("version")
    Integer version = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("verbose")
    Boolean verbose = null;

    public GetRequest(String id, Integer version, Boolean verbose) {
        this.id = id;
        this.version = version;
        this.verbose = verbose;
    }

    public GetRequest(String id) {
        this.id = id;
    }
}

final class JWTGetRequest {
    @JsonProperty("id")
    String id;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("version")
    String version = null;

    public JWTGetRequest(String id, String version) {
        this.id = id;
        this.version = version;
    }
}

final class RevokeRequest {
    @JsonProperty("id")
    String id;

    public RevokeRequest(String id) {
        this.id = id;
    }
}

final class SignRequest {
    @JsonProperty("id")
    String id;

    @JsonProperty("message")
    String message;

    public SignRequest(String id, String message) {
        this.id = id;
        this.message = message;
    }
}

final class JWTSignRequest {
    @JsonProperty("id")
    String id;

    @JsonProperty("payload")
    String payload;

    public JWTSignRequest(String id, String payload) {
        this.id = id;
        this.payload = payload;
    }
}


final class VerifyRequest {
    @JsonProperty("id")
    String id;

    @JsonProperty("message")
    String message;

    @JsonProperty("signature")
    String signature;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("version")
    Integer version = null;

    public VerifyRequest(String id, String message, String signature) {
        this.id = id;
        this.message = message;
        this.signature = signature;
    }

    public VerifyRequest(String id, String message, String signature, Integer version) {
        this.id = id;
        this.message = message;
        this.signature = signature;
        this.version = version;
    }
}

final class JWTVerifyRequest {
    @JsonProperty("jws")
    String jws;

    public JWTVerifyRequest(String jws) {
        this.jws = jws;
    }
}


final class EncryptRequest {
    @JsonProperty("id")
    String id;

    @JsonProperty("plain_text")
    String plainText;

    public EncryptRequest(String id, String plainText) {
        this.id = id;
        this.plainText = plainText;
    }
}

final class DecryptRequest {
    @JsonProperty("id")
    String id;

    @JsonProperty("cipher_text")
    String cipherText;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("version")
    Integer version = null;

    public DecryptRequest(String id, String cipherText, Integer version) {
        this.id = id;
        this.cipherText = cipherText;
        this.version = version;
    }

    public DecryptRequest(String id, String cipherText) {
        this.id = id;
        this.cipherText = cipherText;
    }
}

final class DeleteRequest {
    @JsonProperty("id")
    String id;

    public DeleteRequest(String id) {
        this.id = id;
    }
}

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
    public ListResponse list(ListRequest request) throws PangeaException, PangeaAPIException{
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
     * Store a Pangea Token
     * @pangea.description Store a pangea token in vault service
     * @param request - request parameters to send to /secret/store endpoint
     * @return SecretStoreResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public SecretStoreResponse pangeaTokenStore(PangeaTokenStoreRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/secret/store", request , SecretStoreResponse.class);
    }

    /**
     * Rotate a secret
     * @pangea.description Rotate a secret in vault service
     * @param id - secret id to rotate
     * @param secret - new secret value
     * @return SecretRotateResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public SecretRotateResponse secretRotate(String id, String secret) throws PangeaException, PangeaAPIException{
        return doPost("/v1/secret/rotate", new SecretRotateRequestBuilder(id, secret).build() , SecretRotateResponse.class);
    }

    /**
     * Rotate a Pangea Token
     * @pangea.description Rotate a Pangea Token in vault service
     * @param id - pangea token id to rotate
     * @return SecretRotateResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public SecretRotateResponse pangeaTokenRotate(String id) throws PangeaException, PangeaAPIException{
        return doPost("/v1/secret/rotate", new SecretRotateRequestBuilder(id, null).build() , SecretRotateResponse.class);
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
     * Sign with JWT
     * @pangea.description sign a payload with a JWT
     * @param id - key id to sign payload
     * @param payload - message to sign
     * @return JWTSignResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public JWTSignResponse jwtSign(String id, String payload) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/sign/jwt", new JWTSignRequest(id, payload) , JWTSignResponse.class);
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

    /**
     * Verify a JWT signature
     * @pangea.description Verify a JWS
     * @param jws - signature to verify
     * @return JWTVerifyResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public JWTVerifyResponse jwtVerify(String jws) throws PangeaException, PangeaAPIException{
        return doPost("/v1/key/verify/jwt", new JWTVerifyRequest(jws) , JWTVerifyResponse.class);
    }

    /**
     * Get JWK
     * @pangea.description Get a JWK
     * @param id - item id to get
     * @return GetResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public JWTGetResponse jwtGet(String id) throws PangeaException, PangeaAPIException{
        return doPost("/v1/get/jwk", new JWTGetRequest(id, null) , JWTGetResponse.class);
    }

    /**
     * Get JWK
     * @pangea.description Get a JWK
     * @param id - item id to get
     * @param version - item version/versions to get
     * @return GetResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * // TODO:
     * }
     */
    public JWTGetResponse jwtGet(String id, String version) throws PangeaException, PangeaAPIException{
        return doPost("/v1/get/jwk", new JWTGetRequest(id, version) , JWTGetResponse.class);
    }


}
