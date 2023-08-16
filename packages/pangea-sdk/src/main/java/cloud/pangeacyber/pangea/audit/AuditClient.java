package cloud.pangeacyber.pangea.audit;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.audit.arweave.*;
import cloud.pangeacyber.pangea.audit.models.*;
import cloud.pangeacyber.pangea.audit.requests.*;
import cloud.pangeacyber.pangea.audit.responses.*;
import cloud.pangeacyber.pangea.audit.results.*;
import cloud.pangeacyber.pangea.audit.utils.*;
import cloud.pangeacyber.pangea.exceptions.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

final class RootRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tree_size")
	Integer treeSize;

	public RootRequest(Integer treeSize) {
		this.treeSize = treeSize;
	}
}

final class LogRequest extends BaseRequest {

	@JsonProperty("event")
	IEvent event;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("signature")
	String signature;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("public_key")
	String publicKey;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("prev_root")
	String prevRoot;

	public LogRequest(IEvent event, Boolean verbose, String signature, String publicKey, String prevRoot) {
		this.event = event;
		this.verbose = verbose;
		this.signature = signature;
		this.publicKey = publicKey;
		this.prevRoot = prevRoot;
	}
}

public class AuditClient extends BaseClient {

	public static final String serviceName = "audit";

	LogSigner signer;
	Map<Integer, PublishedRoot> publishedRoots;
	boolean allowServerRoots = true; // In case of Arweave failure, ask the server for the roots
	String prevUnpublishedRoot = null;
	Map<String, Object> pkInfo = null;
	String tenantID = null;
	private static final boolean supportMultiConfig = true;
	Class<?> customSchemaClass = null;

	public AuditClient(Builder builder) {
		super(builder, serviceName, supportMultiConfig);
		if (builder.privateKeyFilename != null) {
			this.signer = new LogSigner(builder.privateKeyFilename);
		} else {
			this.signer = null;
		}
		this.tenantID = builder.tenantID;
		this.pkInfo = builder.pkInfo;
		publishedRoots = new HashMap<Integer, PublishedRoot>();
		this.customSchemaClass = builder.customSchemaClass;
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		String privateKeyFilename = null;
		String tenantID = null;
		Config config;
		Map<String, Object> pkInfo = null;
		Class<?> customSchemaClass = StandardEvent.class;

		public Builder(Config config) {
			super(config);
		}

		public AuditClient build() {
			return new AuditClient(this);
		}

		public Builder withPrivateKey(String privateKeyFilename) {
			this.privateKeyFilename = privateKeyFilename;
			return this;
		}

		public Builder withTenantID(String tenantID) {
			this.tenantID = tenantID;
			return this;
		}

		public Builder withPkInfo(Map<String, Object> pkInfo) {
			this.pkInfo = pkInfo;
			return this;
		}

		public <EventType extends IEvent> Builder withCustomSchema(Class<EventType> customSchemaClass) {
			this.customSchemaClass = customSchemaClass;
			return this;
		}
	}

	private LogResponse logPost(IEvent event, Boolean verbose, String signature, String publicKey, boolean verify)
		throws PangeaException, PangeaAPIException {
		String prevRoot = null;
		if (verify) {
			verbose = true;
			prevRoot = this.prevUnpublishedRoot;
		}
		LogRequest request = new LogRequest(event, verbose, signature, publicKey, prevRoot);
		return post("/v1/log", request, LogResponse.class);
	}

	private LogResponse doLog(IEvent event, LogConfig config) throws PangeaException, PangeaAPIException {
		String signature = null;
		String publicKey = null;

		if (this.tenantID != null) {
			event.setTenantID(this.tenantID);
		}

		if (config.getSignLocal() == true) {
			if (this.signer == null) {
				throw new SignerException("Signer not initialized", null);
			} else {
				String canEvent;
				try {
					canEvent = IEvent.canonicalize(event);
				} catch (Exception e) {
					throw new SignerException("Failed to convert event to string", e);
				}
				signature = this.signer.sign(canEvent);
				publicKey = this.getPublicKeyData();
			}
		}

		LogResponse response = logPost(event, config.getVerbose(), signature, publicKey, config.getVerify());
		processLogResponse(response.getResult(), config.getVerify());
		return response;
	}

	private String getPublicKeyData() throws PangeaException {
		ObjectMapper mapper = JsonMapper
			.builder()
			.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
			.build();
		if (this.pkInfo == null) {
			this.pkInfo = new LinkedHashMap<String, Object>();
		}

		this.pkInfo.put("key", this.signer.getPublicKey());
		this.pkInfo.put("algorithm", this.signer.getAlgorithm());

		try {
			return mapper.writeValueAsString(this.pkInfo);
		} catch (JsonProcessingException e) {
			throw new PangeaException("Failed to stringify public key info", e);
		}
	}

	private void processLogResponse(LogResult result, boolean verify) throws VerificationFailed, PangeaException {
		String newUnpublishedRoot = result.getUnpublishedRoot();
		result.setEventEnvelope(EventEnvelope.fromRaw(result.getRawEnvelope(), (Class<IEvent>) this.customSchemaClass));
		if (verify) {
			EventEnvelope.verifyHash(result.getRawEnvelope(), result.getHash());
			result.verifySignature();
			if (newUnpublishedRoot != null) {
				result.membershipVerification =
					Verification.verifyMembershipProof(
						newUnpublishedRoot,
						result.getHash(),
						result.getMembershipProof()
					);
				if (result.getConsistencyProof() != null && this.prevUnpublishedRoot != null) {
					ConsistencyProof conProof = Verification.decodeConsistencyProof(result.getConsistencyProof());
					result.consistencyVerification =
						Verification.verifyConsistencyProof(newUnpublishedRoot, this.prevUnpublishedRoot, conProof);
				}
			}
		}

		if (newUnpublishedRoot != null) {
			this.prevUnpublishedRoot = newUnpublishedRoot;
		}
	}

	/**
	 * Log an entry
	 * @pangea.description Create a log entry in the Secure Audit Log.
	 * @pangea.operationId audit_post_v1_log
	 * @param event event to log
	 * @param config
	 * @return LogResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * StandardEvent event = new StandardEvent
	 * 	.Builder("Hello, World!")
	 * 	.action("Login")
	 * 	.actor("Terminal")
	 * 	.build();
	 * LogConfig config = new LogConfig.Builder().build();
	 * 
	 * LogResponse response = client.log(event, config);
	 * }
	 */
	public LogResponse log(IEvent event, LogConfig config) throws PangeaException, PangeaAPIException {
		if (config == null) {
			config = new LogConfig.Builder().build();
		}
		return doLog(event, config);
	}

	private RootResponse rootPost(Integer treeSize) throws PangeaException, PangeaAPIException {
		RootRequest request = new RootRequest(treeSize);
		return post("/v1/root", request, RootResponse.class);
	}

	/**
	 * Get last root
	 * @pangea.description Get last root from Pangea Server
	 * @return RootResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * RootResponse response = client.getRoot();
	 * }
	 */
	public RootResponse getRoot() throws PangeaException, PangeaAPIException {
		return rootPost(null);
	}

	/**
	 * Tamperproof verification
	 * @pangea.description Returns current root hash and consistency proof.
	 * @pangea.operationId audit_post_v1_root
	 * @param treeSize tree size to get root
	 * @return RootResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * RootResponse response = client.getRoot(treeSize);
	 * }
	 */
	public RootResponse getRoot(int treeSize) throws PangeaException, PangeaAPIException {
		return rootPost(treeSize);
	}

	private void processSearchResult(ResultsOutput result, SearchConfig config)
		throws PangeaException, PangeaAPIException {
		if (config == null) {
			config = new SearchConfig.Builder().build();
		}

		for (SearchEvent searchEvent : result.getEvents()) {
			searchEvent.setEventEnvelope(
				EventEnvelope.fromRaw(searchEvent.getRawEnvelope(), (Class<IEvent>) this.customSchemaClass)
			);
		}

		if (config.getVerifyEvents()) {
			for (SearchEvent searchEvent : result.getEvents()) {
				EventEnvelope.verifyHash(searchEvent.getRawEnvelope(), searchEvent.getHash());
				searchEvent.verifySignature();
			}
		}

		Root root = result.getRoot();
		Root unpublishedRoot = result.getUnpublishedRoot();

		if (config.getVerifyConsistency()) {
			if (root != null) {
				updatePublishedRoots(result);
			}

			for (SearchEvent searchEvent : result.getEvents()) {
				searchEvent.verifyMembershipProof(
					searchEvent.isPublished() ? root.getRootHash() : unpublishedRoot.getRootHash()
				);
				searchEvent.verifyConsistency(publishedRoots);
			}
		}
	}

	private void updatePublishedRoots(ResultsOutput result) {
		Root root = result.getRoot();
		if (root == null) {
			return;
		}

		Set<Integer> treeSizes = new HashSet<Integer>();
		for (SearchEvent searchEvent : result.getEvents()) {
			if (searchEvent.isPublished()) {
				int leafIndex = searchEvent.getLeafIndex();
				treeSizes.add(leafIndex + 1);
				if (leafIndex > 0) {
					treeSizes.add(leafIndex);
				}
			}
		}

		treeSizes.add(root.getSize());
		treeSizes.removeAll(publishedRoots.keySet());
		Integer[] sizes = new Integer[treeSizes.size()];

		Map<Integer, PublishedRoot> arweaveRoots;
		if (!treeSizes.isEmpty()) {
			Arweave arweave = new Arweave(root.getTreeName());
			arweaveRoots = arweave.getPublishedRoots(treeSizes.toArray(sizes));
		} else {
			return;
		}

		for (Integer treeSize : sizes) {
			if (arweaveRoots.containsKey(treeSize)) {
				PublishedRoot pubRoot = arweaveRoots.get(treeSize);
				pubRoot.setSource("arweave");
				this.publishedRoots.put(treeSize, pubRoot);
			} else if (this.allowServerRoots) {
				RootResponse response;
				try {
					response = this.getRoot(treeSize);
					root = response.getResult().getRoot();
					PublishedRoot pubRoot = new PublishedRoot(
						root.getSize(),
						root.getRootHash(),
						root.getPublishedAt(),
						root.getConsistencyProof(),
						"pangea"
					);
					this.publishedRoots.put(treeSize, pubRoot);
				} catch (Exception e) {
					break;
				}
			}
		}
	}

	/**
	 * Search
	 * @pangea.description Perform a search of logs according to input param. By default verify logs consistency and events hash and signature.
	 * @pangea.operationId audit_post_v1_search
	 * @param request
	 * @param config
	 * @return SearchResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SearchRequest searchRequest = new SearchRequest
	 * 	.Builder("message:\"\"").limit(10).build();
	 * SearchConfig searchConfig = new SearchConfig.Builder().build();
	 * 
	 * SearchResponse response = client.search(searchRequest, searchConfig);
	 * }
	 */
	public SearchResponse search(SearchRequest request, SearchConfig config)
		throws PangeaException, PangeaAPIException {
		SearchResponse response = post("/v1/search", request, SearchResponse.class);
		processSearchResult(response.getResult(), config);
		return response;
	}

	private ResultsResponse resultPost(ResultRequest request, SearchConfig config)
		throws PangeaException, PangeaAPIException {
		ResultsResponse response = post("/v1/results", request, ResultsResponse.class);
		processSearchResult(response.getResult(), config);
		return response;
	}

	/**
	 * Results of a search
	 * @pangea.description Fetch paginated results of a previously executed search. By default: `verifyEvents` is true and `verifyConsistency` is false.
	 * @pangea.operationId audit_post_v1_results
	 * @param request
	 * @param config
	 * @return ResultsResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * ResultRequest request = new ResultRequest
	 * 	.Builder("pas_sqilrhruwu54uggihqj3aie24wrctakr")
	 * 	.limit(3)
	 * 	.offset(0)
	 * 	.build();
	 * SearchConfig searchConfig = new SearchConfig.Builder().build();
	 * 
	 * ResultsResponse response = client.results(request, searchConfig);
	 * }
	 */
	public ResultsResponse results(ResultRequest request, SearchConfig config)
		throws PangeaException, PangeaAPIException {
		return resultPost(request, config);
	}
}
