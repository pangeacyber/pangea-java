package cloud.pangeacyber.pangea.vault;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.vault.models.ItemVersionState;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.responses.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
final class JWKGetRequest extends BaseRequest {

	@NonNull
	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	String version;
}

@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
@Value
final class StateChangeRequest extends BaseRequest {

	/** The item ID. */
	@NonNull
	@JsonProperty("id")
	String id;

	/** The new state of the item version. */
	@NonNull
	@JsonProperty("state")
	ItemVersionState state;

	/** The item version. */
	@JsonProperty("version")
	int version;

	/** Period of time for the destruction of a compromised key. */
	@JsonProperty("destroy_period")
	String destroyPeriod;
}

@EqualsAndHashCode(callSuper = true)
@Value
final class SignRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	@JsonProperty("message")
	String message;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	Integer version;
}

@EqualsAndHashCode(callSuper = true)
@Value
final class JWTSignRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	@JsonProperty("payload")
	String payload;
}

@EqualsAndHashCode(callSuper = true)
@Value
final class VerifyRequest extends BaseRequest {

	/** The item ID. */
	@NonNull
	@JsonProperty("id")
	String id;

	/** A message to be verified */
	@NonNull
	@JsonProperty("message")
	String message;

	/** The message signature. */
	@NonNull
	@JsonProperty("signature")
	String signature;

	/** The item version. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	Integer version;
}

@EqualsAndHashCode(callSuper = true)
@Value
final class JWTVerifyRequest extends BaseRequest {

	@JsonProperty("jws")
	String jws;
}

@EqualsAndHashCode(callSuper = true)
@Value
final class DeleteRequest extends BaseRequest {

	@JsonProperty("id")
	String id;
}

public class VaultClient extends BaseClient {

	public static final String serviceName = "vault";

	public VaultClient(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public VaultClient build() {
			return new VaultClient(this);
		}
	}

	/**
	 * List
	 * @pangea.description Retrieve a list of secrets, keys and folders.
	 * @pangea.operationId vault_post_v2_get_bulk
	 * @param request Request parameters.
	 * @return A list of items.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * final var filter = new HashMap<String, String>();
	 * filter.put("folder", "/tmp");
	 * final var response = client.getBulk(GetBulkRequest.builder().filter(filter).build());
	 * }
	 */
	public ListResponse getBulk(final GetBulkRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/get_bulk", request, ListResponse.class);
	}

	/**
	 * State change
	 * @pangea.description Change the state of a specific version of a secret or key.
	 * @pangea.operationId vault_post_v2_state_change
	 * @param id Item id to change.
	 * @param state State to set to item version.
	 * @param version Item version to change.
	 * @return StateChangeResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * final var stateChangeResponse = client.stateChange("id", ItemVersionState.DEACTIVATED);
	 * }
	 */
	public StateChangeResponse stateChange(String id, ItemVersionState state, int version, String destroyPeriod)
		throws PangeaException, PangeaAPIException {
		return post(
			"/v2/state/change",
			new StateChangeRequest(id, state, version, destroyPeriod),
			StateChangeResponse.class
		);
	}

	/**
	 * Delete
	 * @pangea.description Delete a secret or key.
	 * @pangea.operationId vault_post_v2_delete
	 * @param id - item id to delete
	 * @return DeleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DeleteResponse deleteResponse = client.delete("id");
	 * }
	 */
	public DeleteResponse delete(String id) throws PangeaException, PangeaAPIException {
		return post("/v2/delete", new DeleteRequest(id), DeleteResponse.class);
	}

	/**
	 * Retrieve
	 * @pangea.description Retrieve a secret or key, and any associated information.
	 * @pangea.operationId vault_post_v2_get
	 * @param request - request to /get endpoint
	 * @return GetResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * GetResponse getResponse = client.get(
	 * 	new GetRequest.GetRequestBuilder("id").build()
	 * );
	 * }
	 */
	public GetResponse get(GetRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/get", request, GetResponse.class);
	}

	/**
	 * List
	 * @pangea.description Retrieve a list of secrets, keys and folders, and their associated information.
	 * @pangea.operationId vault_post_v2_list
	 * @param request - request parameters to send to list endpoint
	 * @return ListResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * ListResponse listResponse = client.list(
	 * 	new ListRequest.ListRequestBuilder().build()
	 * );
	 * }
	 */
	public ListResponse list(ListRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/list", request, ListResponse.class);
	}

	/**
	 * Update
	 * @pangea.description Update information associated with a secret or key.
	 * @pangea.operationId vault_post_v2_update
	 * @param request - request parameters to send to update endpoint
	 * @return UpdateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UpdateResponse updateResponse = client.update(
	 * 	new UpdateRequest.UpdateRequestBuilder("id")
	 * 	.setFolder("updated")
	 * 	.build()
	 * );
	 * }
	 */
	public UpdateResponse update(UpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/update", request, UpdateResponse.class);
	}

	/**
	 * Store a secret
	 * @pangea.description Store a secret in vault service.
	 * @pangea.operationId vault_post_v2_secret_store 1
	 * @param request - request parameters to send to /secret/store endpoint
	 * @return SecretStoreResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SecretStoreResponse storeResponse =
	 * 	client.secretStore(new SecretStoreRequest.SecretStoreRequestBuilder("mysecret", "mysecretname").build());
	 * }
	 */
	public SecretStoreResponse secretStore(SecretStoreRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/secret/store", request, SecretStoreResponse.class);
	}

	/**
	 * Store a Pangea Token
	 * @pangea.description Store a pangea token in vault service.
	 * @pangea.operationId vault_post_v2_secret_store 2
	 * @param request - request parameters to send to /secret/store endpoint
	 * @return SecretStoreResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SecretStoreResponse storeResponse =
	 * 	client.pangeaTokenStore(new PangeaTokenStoreRequest.PangeaTokenStoreRequestBuilder("mytoken", "mytokenname").build());
	 * }
	 */
	public SecretStoreResponse pangeaTokenStore(PangeaTokenStoreRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/secret/store", request, SecretStoreResponse.class);
	}

	/**
	 * Rotate a secret
	 * @pangea.description Rotate a secret in vault service.
	 * @pangea.operationId vault_post_v2_secret_rotate 1
	 * @param request - secret rotate request
	 * @return SecretRotateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SecretRotateResponse rotateResponse = client.secretRotate(
	 * 	new SecretRotateRequest.SecretRotateRequestBuilder("secretid", "mynewsecret")
	 * 		.setRotationState(ItemVersionState.SUSPENDED)
	 * 		.build()
	 * );
	 * }
	 */
	public SecretRotateResponse secretRotate(SecretRotateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/secret/rotate", request, SecretRotateResponse.class);
	}

	/**
	 * Rotate a Pangea Token
	 * @pangea.description Rotate a Pangea Token in vault service.
	 * @pangea.operationId vault_post_v2_secret_rotate 2
	 * @param request - pangea token store request
	 * @return SecretRotateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SecretRotateResponse rotateResponse = client.pangeaTokenRotate(
	 * 	new PangeaTokenStoreRequest.PangeaTokenRotateRequestBuilder("tokenid", "3m")
	 * 		.build()
	 * );
	 * }
	 */
	public SecretRotateResponse pangeaTokenRotate(PangeaTokenRotateRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/secret/rotate", request, SecretRotateResponse.class);
	}

	/**
	 * Symmetric generate
	 * @pangea.description Generate a symmetric key.
	 * @pangea.operationId vault_post_v2_key_generate 2
	 * @param request - request parameters to send to /key/generate endpoint
	 * @return SymmetricGenerateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SymmetricGenerateRequest generateRequest = new SymmetricGenerateRequest.SymmetricGenerateRequestBuilder(
	 * 	SymmetricAlgorithm.AES128_CFB,
	 * 	KeyPurpose.ENCRYPTION,
	 * 	"keyname"
	 * ).build();
	 * SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
	 * }
	 */
	public SymmetricGenerateResponse symmetricGenerate(SymmetricGenerateRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/key/generate", request, SymmetricGenerateResponse.class);
	}

	/**
	 * Asymmetric generate
	 * @pangea.description Generate an asymmetric key.
	 * @pangea.operationId vault_post_v2_key_generate 1
	 * @param request - request parameters to send to /key/generate endpoint
	 * @return AsymmetricGenerateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * AsymmetricGenerateRequest generateRequest = new AsymmetricGenerateRequest.AsymmetricGenerateRequestBuilder(
	 * 	AsymmetricAlgorithm.ED25519,
	 * 	KeyPurpose.SIGNING,
	 * 	"keyname"
	 * ).build();
	 * AsymmetricGenerateResponse generateResp = client.asymmetricGenerate(generateRequest);
	 * }
	 */
	public AsymmetricGenerateResponse asymmetricGenerate(AsymmetricGenerateRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/key/generate", request, AsymmetricGenerateResponse.class);
	}

	/**
	 * Asymmetric store
	 * @pangea.description Import an asymmetric key.
	 * @pangea.operationId vault_post_v2_key_store 1
	 * @param request - request parameters to send to /key/store endpoint
	 * @return AsymmetricStoreResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * AsymmetricStoreRequest storeRequest = new AsymmetricStoreRequest.AsymmetricStoreRequestBuilder(
	 * 	"encodedprivatekey",
	 * 	"encodedpublickey",
	 * 	AsymmetricAlgorithm.ED25519,
	 * 	KeyPurpose.SIGNING,
	 * 	"keyname"
	 * ).build();
	 * AsymmetricStoreResponse storeResp = client.asymmetricStore(storeRequest);
	 * }
	 */
	public AsymmetricStoreResponse asymmetricStore(AsymmetricStoreRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/key/store", request, AsymmetricStoreResponse.class);
	}

	/**
	 * Symmetric store
	 * @pangea.description Import a symmetric key.
	 * @pangea.operationId vault_post_v2_key_store 2
	 * @param request - request parameters to send to /key/store endpoint
	 * @return SymmetricStoreResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SymmetricStoreRequest storeRequest = new SymmetricStoreRequest.SymmetricStoreRequestBuilder(
	 * 	"encodedkey"
	 * 	SymmetricAlgorithm.AES,
	 * 	KeyPurpose.ENCRYPTION,
	 * 	"keyname"
	 * ).build();
	 * SymmetricStoreResponse storeResp = client.symmetricStore(storeRequest);
	 * }
	 */
	public SymmetricStoreResponse symmetricStore(SymmetricStoreRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/key/store", request, SymmetricStoreResponse.class);
	}

	/**
	 * Key rotate
	 * @pangea.description Manually rotate a symmetric or asymmetric key.
	 * @pangea.operationId vault_post_v2_key_rotate
	 * @param request - request parameters to send to /key/rotate endpoint
	 * @return KeyRotateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * KeyRotateResponse rotateResponse = client.keyRotate(
	 * 	new KeyRotateRequest.KeyRotateRequestBuilder("keyid", ItemVersionState.SUSPENDED).build()
	 * );
	 * }
	 */
	public KeyRotateResponse keyRotate(KeyRotateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/key/rotate", request, KeyRotateResponse.class);
	}

	/**
	 * Encrypt
	 * @pangea.description Encrypt a message using a key.
	 * @pangea.operationId vault_post_v2_key_encrypt
	 * @param request - request to be send to /key/encrypt
	 * @return EncryptResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * EncryptResponse encryptResponse = client.encrypt(
	 * 	new EncryptRequest.EncryptRequestBuilder("keyid", "base64message").setVersion(2).build()
	 * );
	 * }
	 */
	public EncryptResponse encrypt(EncryptRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/encrypt", request, EncryptResponse.class);
	}

	/**
	 * Decrypt
	 * @pangea.description Decrypt a message using a key.
	 * @pangea.operationId vault_post_v2_key_decrypt
	 * @param request - request to be send to /key/decrypt
	 * @return DecryptResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DecryptResponse decryptResponse = client.decrypt(
	 * 	new DecryptRequest.DecryptRequestBuilder("keyid", "validciphertext")
	 * 		.setVersion(2)
	 * 		.build()
	 * );
	 * }
	 */
	public DecryptResponse decrypt(DecryptRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/decrypt", request, DecryptResponse.class);
	}

	/**
	 * Sign
	 * @pangea.description Sign a message using a key.
	 * @pangea.operationId vault_post_v2_key_sign
	 * @param id - key id to sign message
	 * @param message - message to sign
	 * @return SignResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SignResponse signResponse = client.sign("keyid", "base64data2sign");
	 * }
	 */
	public SignResponse sign(String id, String message) throws PangeaException, PangeaAPIException {
		return post("/v2/sign", new SignRequest(id, message, null), SignResponse.class);
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
	 * SignResponse signResponse = client.sign("keyid", "base64data2sign", 2);
	 * }
	 */
	public SignResponse sign(String id, String message, int version) throws PangeaException, PangeaAPIException {
		return post("/v2/sign", new SignRequest(id, message, version), SignResponse.class);
	}

	/**
	 * JWT Sign
	 * @pangea.description Sign a JSON Web Token (JWT) using a key.
	 * @pangea.operationId vault_post_v2_key_sign_jwt
	 * @param id - key id to sign payload
	 * @param payload - message to sign
	 * @return JWTSignResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * String payload = """
	 *      {'message': 'message to sign', 'data': 'Some extra data'}
	 *       """;
	 * JWTSignResponse signResponse1 = client.jwtSign("keyid", payload);
	 * }
	 */
	public JWTSignResponse jwtSign(String id, String payload) throws PangeaException, PangeaAPIException {
		return post("/v2/jwt/sign", new JWTSignRequest(id, payload), JWTSignResponse.class);
	}

	/**
	 * Verify
	 * @pangea.description Verify a signature using a key.
	 * @pangea.operationId vault_post_v2_key_verify
	 * @param id - key id to verify message/signature
	 * @param message - message to verify
	 * @param signature - signature to verify
	 * @return VerifyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * VerifyResponse verifyResponse = client.verify("keyid", "data2verify", "signature");
	 * }
	 */
	public VerifyResponse verify(String id, String message, String signature)
		throws PangeaException, PangeaAPIException {
		return this.verify(id, message, signature, null);
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
	 * VerifyResponse verifyResponse = client.verify("keyid", "data2verify", "signature", 1);
	 * }
	 */
	public VerifyResponse verify(String id, String message, String signature, Integer version)
		throws PangeaException, PangeaAPIException {
		return post("/v2/verify", new VerifyRequest(id, message, signature, version), VerifyResponse.class);
	}

	/**
	 * JWT Verify
	 * @pangea.description Verify the signature of a JSON Web Token (JWT).
	 * @pangea.operationId vault_post_v2_key_verify_jwt
	 * @param jws - signature to verify
	 * @return JWTVerifyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * JWTVerifyResponse verifyResponse = client.jwtVerify(signResponse.getResult().getJws());
	 * }
	 */
	public JWTVerifyResponse jwtVerify(String jws) throws PangeaException, PangeaAPIException {
		return post("/v2/jwt/verify", new JWTVerifyRequest(jws), JWTVerifyResponse.class);
	}

	/**
	 * JWT Retrieve
	 * @pangea.description Retrieve a key in JWK format.
	 * @pangea.operationId vault_post_v2_get_jwk
	 * @param id - item id to get
	 * @return GetResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * JWKGetResponse getResponse = client.jwkGet("jwkid");
	 * }
	 */
	public JWKGetResponse jwkGet(String id) throws PangeaException, PangeaAPIException {
		return post("/v2/jwk/get", new JWKGetRequest(id, null), JWKGetResponse.class);
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
	 * JWKGetResponse getResponse = client.jwkGet("jwkid", 2);
	 * }
	 */
	public JWKGetResponse jwkGet(String id, String version) throws PangeaException, PangeaAPIException {
		return post("/v2/jwk/get", new JWKGetRequest(id, version), JWKGetResponse.class);
	}

	/**
	 * Create
	 * @pangea.description Creates a folder.
	 * @pangea.operationId vault_post_v2_folder_create
	 * @param request - request parameters to send to /folder/create endpoint
	 * @return FolderCreateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FolderCreateResponse createParentResp = client.folderCreate(
	 * 	new FolderCreateRequest.Builder("folder_name", "parent/folder/name").build()
	 * );
	 * }
	 */
	public FolderCreateResponse folderCreate(FolderCreateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/folder/create", request, FolderCreateResponse.class);
	}

	/**
	 * Encrypt structured
	 * @pangea.description Encrypt parts of a JSON object.
	 * @pangea.operationId vault_post_v2_key_encrypt_structured
	 * @param request Request parameters.
	 * @return Encrypted result.
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * var request = new EncryptStructuredRequest.Builder<SomeModel>(
	 *     key,
	 *     data,
	 *     "$.field1[2:4]"
	 * ).build();
	 * var encrypted = client.encryptStructured(request);
	 * }
	 */
	public <K, V, T extends Map<K, V>> EncryptStructuredResponse<K, V, T> encryptStructured(
		EncryptStructuredRequest<K, V, T> request
	) throws PangeaException, PangeaAPIException {
		return post("/v2/encrypt_structured", request, new TypeReference<EncryptStructuredResponse<K, V, T>>() {});
	}

	/**
	 * Decrypt structured
	 * @pangea.description Decrypt parts of a JSON object.
	 * @pangea.operationId vault_post_v2_key_decrypt_structured
	 * @param request Request parameters.
	 * @return Decrypted result.
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * var request = new EncryptStructuredRequest.Builder<SomeModel>(
	 *     key,
	 *     data,
	 *     "$.field1[2:4]"
	 * ).build();
	 * var encrypted = client.decryptStructured(request);
	 * }
	 */
	public <K, V, T extends Map<K, V>> EncryptStructuredResponse<K, V, T> decryptStructured(
		EncryptStructuredRequest<K, V, T> request
	) throws PangeaException, PangeaAPIException {
		return post("/v2/decrypt_structured", request, new TypeReference<EncryptStructuredResponse<K, V, T>>() {});
	}

	/**
	 * Encrypt transform
	 * @pangea.description Encrypt using a format preserving algorithm (FPE).
	 * @pangea.operationId vault_post_v2_key_encrypt_transform
	 * @param request Request parameters.
	 * @return Encrypted response.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var encrypted = client.encryptTransform(
	 * 	new EncryptTransformRequest.Builder(
	 * 		"pvi_[...]",
	 * 		"123-4567-8901",
	 * 		TransformAlphabet.ALPHANUMERIC
	 * 	).tweak("MTIzMTIzMT==").build()
	 * );
	 * }
	 */
	public EncryptTransformResponse encryptTransform(EncryptTransformRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/encrypt_transform", request, EncryptTransformResponse.class);
	}

	/**
	 * Decrypt transform
	 * @pangea.description Decrypt using a format preserving algorithm (FPE).
	 * @pangea.operationId vault_post_v2_key_decrypt_transform
	 * @param request Request parameters.
	 * @return Decrypted response.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * var decrypted = client.decryptTransform(
	 * 	new DecryptTransformRequest.Builder(
	 * 		"pvi_[...]",
	 * 		"tZB-UKVP-MzTM",
	 * 		"MTIzMTIzMT==",
	 * 		TransformAlphabet.ALPHANUMERIC
	 * 	).build()
	 * );
	 * }
	 */
	public DecryptTransformResponse decryptTransform(DecryptTransformRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/decrypt_transform", request, DecryptTransformResponse.class);
	}

	/**
	 * Export
	 * @pangea.description Export a symmetric or asymmetric key.
	 * @pangea.operationId vault_post_v2_export
	 * @param request Request parameters.
	 * @return Exported result.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * // Generate an exportable key.
	 * final var generateRequest = new AsymmetricGenerateRequest.Builder(
	 * 	AsymmetricAlgorithm.RSA4096_OAEP_SHA512,
	 * 	KeyPurpose.ENCRYPTION,
	 * 	"a-name-for-the-key"
	 * )
	 * 	.exportable(true)
	 * 	.build();
	 * final var generated = client.asymmetricGenerate(generateRequest);
	 * final var key = generated.getResult().getId();
	 *
	 * // Then it can be exported whenever needed.
	 * final var request = ExportRequest.builder(key).build();
	 * final var exported = client.export(request);
	 * }
	 */
	public ExportResponse export(ExportRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/export", request, ExportResponse.class);
	}
}
