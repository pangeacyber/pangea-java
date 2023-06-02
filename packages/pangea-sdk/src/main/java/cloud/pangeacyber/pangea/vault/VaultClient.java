package cloud.pangeacyber.pangea.vault;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.vault.models.ItemVersionState;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.responses.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class JWKGetRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	String version = null;

	public JWKGetRequest(String id, String version) {
		this.id = id;
		this.version = version;
	}
}

final class StateChangeRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	@JsonProperty("version")
	int version;

	@JsonProperty("state")
	ItemVersionState state;

	public StateChangeRequest(String id, int version, ItemVersionState state) {
		this.id = id;
		this.version = version;
		this.state = state;
	}
}

final class SignRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	@JsonProperty("message")
	String message;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	Integer version;

	public SignRequest(String id, String message, Integer version) {
		this.id = id;
		this.message = message;
		this.version = version;
	}
}

final class JWTSignRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	@JsonProperty("payload")
	String payload;

	public JWTSignRequest(String id, String payload) {
		this.id = id;
		this.payload = payload;
	}
}

final class VerifyRequest extends BaseRequest {

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

final class JWTVerifyRequest extends BaseRequest {

	@JsonProperty("jws")
	String jws;

	public JWTVerifyRequest(String jws) {
		this.jws = jws;
	}
}

final class DeleteRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	public DeleteRequest(String id) {
		this.id = id;
	}
}

public class VaultClient extends Client {

	public static final String serviceName = "vault";
	private static final boolean supportMultiConfig = false;

	public VaultClient(Config config) {
		super(config, serviceName, supportMultiConfig);
	}

	/**
	 * State change
	 * @pangea.description Change the state of a specific version of a secret or key.
	 * @pangea.operationId _vault_post_v1_state_change
	 * @param id - item id to change
	 * @param version - item version to change
	 * @param state - state to set to item version
	 * @return StateChangeResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public StateChangeResponse stateChange(String id, int version, ItemVersionState state)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/state/change", new StateChangeRequest(id, version, state), StateChangeResponse.class);
	}

	/**
	 * Delete
	 * @pangea.description Delete a secret or key.
	 * @pangea.operationId _vault_post_v1_delete
	 * @param id - item id to delete
	 * @return DeleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public DeleteResponse delete(String id) throws PangeaException, PangeaAPIException {
		return doPost("/v1/delete", new DeleteRequest(id), DeleteResponse.class);
	}

	/**
	 * Retrieve
	 * @pangea.description Retrieve a secret or key, and any associated information.
	 * @pangea.operationId _vault_post_v1_get
	 * @param request - request to /get endpoint
	 * @return GetResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public GetResponse get(GetRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/get", request, GetResponse.class);
	}

	/**
	 * List
	 * @pangea.description Retrieve a list of secrets, keys and folders, and their associated information.
	 * @pangea.operationId _vault_post_v1_list
	 * @param request - request parameters to send to list endpoint
	 * @return ListResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public ListResponse list(ListRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/list", request, ListResponse.class);
	}

	/**
	 * Update
	 * @pangea.description Update information associated with a secret or key.
	 * @pangea.operationId _vault_post_v1_update
	 * @param request - request parameters to send to update endpoint
	 * @return UpdateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public UpdateResponse update(ListRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/update", request, UpdateResponse.class);
	}

	/**
	 * Store a secret
	 * @pangea.description Store a secret in vault service.
	 * @pangea.operationId _vault_post_v1_secret_store 1
	 * @param request - request parameters to send to /secret/store endpoint
	 * @return SecretStoreResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public SecretStoreResponse secretStore(SecretStoreRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/secret/store", request, SecretStoreResponse.class);
	}

	/**
	 * Store a Pangea Token
	 * @pangea.description Store a pangea token in vault service.
	 * @pangea.operationId _vault_post_v1_secret_store 2
	 * @param request - request parameters to send to /secret/store endpoint
	 * @return SecretStoreResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public SecretStoreResponse pangeaTokenStore(PangeaTokenStoreRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/secret/store", request, SecretStoreResponse.class);
	}

	/**
	 * Rotate a secret
	 * @pangea.description Rotate a secret in vault service.
	 * @pangea.operationId _vault_post_v1_secret_rotate 1
	 * @param request - secret rotate request
	 * @return SecretRotateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public SecretRotateResponse secretRotate(SecretRotateRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/secret/rotate", request, SecretRotateResponse.class);
	}

	/**
	 * Rotate a Pangea Token
	 * @pangea.description Rotate a Pangea Token in vault service.
	 * @pangea.operationId _vault_post_v1_secret_rotate 2
	 * @param request - pangea token store request
	 * @return SecretRotateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public SecretRotateResponse pangeaTokenRotate(PangeaTokenStoreRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/secret/rotate", request, SecretRotateResponse.class);
	}

	/**
	 * Symmetric generate
	 * @pangea.description Generate a symmetric key.
	 * @pangea.operationId _vault_post_v1_key_generate 1
	 * @param request - request parameters to send to /key/generate endpoint
	 * @return SymmetricGenerateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public SymmetricGenerateResponse symmetricGenerate(SymmetricGenerateRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/generate", request, SymmetricGenerateResponse.class);
	}

	/**
	 * Asymmetric generate
	 * @pangea.description Generate an asymmetric key.
	 * @pangea.operationId _vault_post_v1_key_generate 2
	 * @param request - request parameters to send to /key/generate endpoint
	 * @return AsymmetricGenerateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public AsymmetricGenerateResponse asymmetricGenerate(AsymmetricGenerateRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/generate", request, AsymmetricGenerateResponse.class);
	}

	/**
	 * Asymmetric store
	 * @pangea.description Import an asymmetric key.
	 * @pangea.operationId _vault_post_v1_key_store 1
	 * @param request - request parameters to send to /key/store endpoint
	 * @return AsymmetricStoreResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public AsymmetricStoreResponse asymmetricStore(AsymmetricStoreRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/store", request, AsymmetricStoreResponse.class);
	}

	/**
	 * Symmetric store
	 * @pangea.description Import a symmetric key.
	 * @pangea.operationId _vault_post_v1_key_store 2
	 * @param request - request parameters to send to /key/store endpoint
	 * @return SymmetricStoreResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public SymmetricStoreResponse symmetricStore(SymmetricStoreRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/store", request, SymmetricStoreResponse.class);
	}

	/**
	 * Key rotate
	 * @pangea.description Manually rotate a symmetric or asymmetric key.
	 * @pangea.operationId _vault_post_v1_key_rotate
	 * @param request - request parameters to send to /key/rotate endpoint
	 * @return KeyRotateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public KeyRotateResponse keyRotate(KeyRotateRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/rotate", request, KeyRotateResponse.class);
	}

	/**
	 * Encrypt
	 * @pangea.description Encrypt a message
	 * @deprecated use encrypt(EncryptRequest request) instead.
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
	public EncryptResponse encrypt(String id, String plainText) throws PangeaException, PangeaAPIException {
		return doPost(
			"/v1/key/encrypt",
			new EncryptRequest.EncryptRequestBuilder(id, plainText).build(),
			EncryptResponse.class
		);
	}

	/**
	 * Encrypt
	 * @pangea.description Encrypt a message
	 * @deprecated use encrypt(EncryptRequest request) instead.
	 * @param id - key id to encrypt message
	 * @param plainText - message to encrypt
	 * @param version - key version to encrypt message
	 * @return EncryptResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public EncryptResponse encrypt(String id, String plainText, int version)
		throws PangeaException, PangeaAPIException {
		return doPost(
			"/v1/key/encrypt",
			new EncryptRequest.EncryptRequestBuilder(id, plainText).setVersion(version).build(),
			EncryptResponse.class
		);
	}

	/**
	 * Encrypt
	 * @pangea.description Encrypt a message using a key.
	 * @pangea.operationId _vault_post_v1_key_encrypt
	 * @param request - request to be send to /key/encrypt
	 * @return EncryptResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public EncryptResponse encrypt(EncryptRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/encrypt", request, EncryptResponse.class);
	}

	/**
	 * Decrypt
	 * @pangea.description Decrypt a message
	 * @deprecated use decrypt(DecryptRequest request) instead.
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
	public DecryptResponse decrypt(String id, String cipherText) throws PangeaException, PangeaAPIException {
		return doPost(
			"/v1/key/decrypt",
			new DecryptRequest.DecryptRequestBuilder(id, cipherText).build(),
			DecryptResponse.class
		);
	}

	/**
	 * Decrypt
	 * @pangea.description Decrypt a message
	 * @deprecated use decrypt(DecryptRequest request) instead.
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
	public DecryptResponse decrypt(String id, String cipherText, Integer version)
		throws PangeaException, PangeaAPIException {
		return doPost(
			"/v1/key/decrypt",
			new DecryptRequest.DecryptRequestBuilder(id, cipherText).setVersion(version).build(),
			DecryptResponse.class
		);
	}

	/**
	 * Decrypt
	 * @pangea.description Decrypt a message using a key.
	 * @pangea.operationId _vault_post_v1_key_decrypt
	 * @param request - request to be send to /key/decrypt
	 * @return DecryptResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public DecryptResponse decrypt(DecryptRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/decrypt", request, DecryptResponse.class);
	}

	/**
	 * Sign
	 * @pangea.description Sign a message using a key.
	 * @pangea.operationId _vault_post_v1_key_sign
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
	public SignResponse sign(String id, String message) throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/sign", new SignRequest(id, message, null), SignResponse.class);
	}

	/**
	 * Sign
	 * @pangea.description sign a message
	 * @param id - key id to sign message
	 * @param message - message to sign
	 * @param version - key version to sign message
	 * @return SignResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public SignResponse sign(String id, String message, int version) throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/sign", new SignRequest(id, message, version), SignResponse.class);
	}

	/**
	 * JWT Sign
	 * @pangea.description Sign a JSON Web Token (JWT) using a key.
	 * @pangea.operationId _vault_post_v1_key_sign_jwt
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
	public JWTSignResponse jwtSign(String id, String payload) throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/sign/jwt", new JWTSignRequest(id, payload), JWTSignResponse.class);
	}

	/**
	 * Verify
	 * @pangea.description Verify a signature using a key.
	 * @pangea.operationId _vault_post_v1_key_verify
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
	public VerifyResponse verify(String id, String message, String signature)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/verify", new VerifyRequest(id, message, signature), VerifyResponse.class);
	}

	/**
	 * Verify
	 * @pangea.description Verify a signature using a key.
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
	public VerifyResponse verify(String id, String message, String signature, Integer version)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/verify", new VerifyRequest(id, message, signature, version), VerifyResponse.class);
	}

	/**
	 * JWT Verify
	 * @pangea.description Verify the signature of a JSON Web Token (JWT).
	 * @pangea.operationId _vault_post_v1_key_verify_jwt
	 * @param jws - signature to verify
	 * @return JWTVerifyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public JWTVerifyResponse jwtVerify(String jws) throws PangeaException, PangeaAPIException {
		return doPost("/v1/key/verify/jwt", new JWTVerifyRequest(jws), JWTVerifyResponse.class);
	}

	/**
	 * JWT Retrieve
	 * @pangea.description Retrieve a key in JWK format.
	 * @pangea.operationId _vault_post_v1_get_jwk
	 * @param id - item id to get
	 * @return GetResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * // TODO:
	 * }
	 */
	public JWKGetResponse jwkGet(String id) throws PangeaException, PangeaAPIException {
		return doPost("/v1/get/jwk", new JWKGetRequest(id, null), JWKGetResponse.class);
	}

	/**
	 * JWT Retrieve
	 * @pangea.description Retrieve a key in JWK format.
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
	public JWKGetResponse jwkGet(String id, String version) throws PangeaException, PangeaAPIException {
		return doPost("/v1/get/jwk", new JWKGetRequest(id, version), JWKGetResponse.class);
	}
}
