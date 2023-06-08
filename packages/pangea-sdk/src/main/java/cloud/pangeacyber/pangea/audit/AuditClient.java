package cloud.pangeacyber.pangea.audit;

import cloud.pangeacyber.pangea.BaseClient;
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

final class RootRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tree_size")
	Integer treeSize;

	public RootRequest(Integer treeSize) {
		this.treeSize = treeSize;
	}
}

final class LogRequest {

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

	public static String serviceName = "audit";
	LogSigner signer;
	Map<Integer, PublishedRoot> publishedRoots;
	boolean allowServerRoots = true; // In case of Arweave failure, ask the server for the roots
	String prevUnpublishedRoot = null;
	Map<String, Object> pkInfo = null;
	String tenantID = null;

	public AuditClient(Builder builder) {
		super(builder);
		if (builder.privateKeyFilename != null) {
			this.signer = new LogSigner(builder.privateKeyFilename);
		} else {
			this.signer = null;
		}
		this.tenantID = builder.tenantID;
		this.pkInfo = builder.pkInfo;
		publishedRoots = new HashMap<Integer, PublishedRoot>();
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		String privateKeyFilename = null;
		String tenantID = null;
		Config config;
		Map<String, Object> pkInfo = null;

		public Builder(Config config) {
			super(config, "audit");
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

	private <EventType extends IEvent> LogResponse doLog(IEvent event, Class<EventType> eventType, LogConfig config)
		throws PangeaException, PangeaAPIException {
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
		processLogResponse(response.getResult(), config.getVerify(), eventType);
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

	private <EventType extends IEvent> void processLogResponse(
		LogResult result,
		boolean verify,
		Class<EventType> eventType
	) throws VerificationFailed, PangeaException {
		String newUnpublishedRoot = result.getUnpublishedRoot();
		result.setEventEnvelope(EventEnvelope.fromRaw(result.getRawEnvelope(), eventType));
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
	 * Log an entry - event, sign, verbose
	 * @pangea.description Log an event to Audit Secure Log. Can select sign event or not and verbosity of the response.
	 * @param event event to log
	 * @param signMode "Unsigned" or "Local"
	 * @param verbose true to more verbose response
	 * @return LogResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
		// FIXME:
	 * }
	 */
	public <EventType extends IEvent> LogResponse log(IEvent event, Class<EventType> eventType, LogConfig config)
		throws PangeaException, PangeaAPIException {
		if (config == null) {
			config = new LogConfig.Builder().build();
		}
		return doLog(event, eventType, config);
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

	private <EventType extends IEvent> void processSearchResult(
		ResultsOutput result,
		Class<EventType> eventType,
		SearchConfig config
	) throws PangeaException, PangeaAPIException {
		if (config == null) {
			config = new SearchConfig.Builder().build();
		}

		for (SearchEvent searchEvent : result.getEvents()) {
			searchEvent.setEventEnvelope(EventEnvelope.fromRaw(searchEvent.getRawEnvelope(), eventType));
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
	 * @param input query filters to perform search
	 * @return SearchResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * SearchInput input = new SearchInput("message:Integration test msg");
	 *
	 * input.setMaxResults(10);
	 *
	 * SearchResponse response = client.search(input);
	 * }
	 */
	public <EventType extends IEvent> SearchResponse search(
		SearchRequest request,
		Class<EventType> eventType,
		SearchConfig config
	) throws PangeaException, PangeaAPIException {
		SearchResponse response = post("/v1/search", request, SearchResponse.class);
		processSearchResult(response.getResult(), eventType, config);
		return response;
	}

	private <EventType extends IEvent> ResultsResponse resultPost(
		ResultRequest request,
		Class<EventType> eventType,
		SearchConfig config
	) throws PangeaException, PangeaAPIException {
		ResultsResponse response = post("/v1/results", request, ResultsResponse.class);
		processSearchResult(response.getResult(), eventType, config);
		return response;
	}

	/**
	 * Results of a search
	 * @pangea.description Fetch paginated results of a previously executed search. By default: `verifyEvents` is true and `verifyConsistency` is false.
	 * @pangea.operationId audit_post_v1_results
	 * @param id A search results identifier returned by the search call.
	 * @param limit Number of audit records to include in a single set of results.
	 * @param offset Offset from the start of the result set to start returning results from.
	 * @return ResultsResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 */
	public <EventType extends IEvent> ResultsResponse results(
		ResultRequest request,
		Class<EventType> eventType,
		SearchConfig config
	) throws PangeaException, PangeaAPIException {
		return resultPost(request, eventType, config);
	}
}
