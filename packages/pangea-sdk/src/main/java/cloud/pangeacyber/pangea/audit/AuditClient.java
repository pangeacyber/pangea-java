package cloud.pangeacyber.pangea.audit;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.audit.arweave.Arweave;
import cloud.pangeacyber.pangea.audit.arweave.PublishedRoot;
import cloud.pangeacyber.pangea.audit.utils.ConsistencyProof;
import cloud.pangeacyber.pangea.audit.utils.Verification;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.SignerException;
import cloud.pangeacyber.pangea.exceptions.VerificationFailed;
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

public class AuditClient extends Client {

	public static String serviceName = "audit";
	LogSigner signer;
	Map<Integer, PublishedRoot> publishedRoots;
	boolean allowServerRoots = true; // In case of Arweave failure, ask the server for the roots
	String prevUnpublishedRoot = null;
	Map<String, Object> pkInfo = null;
	String tenantID = null;

	/**
	 * @deprecated use AuditClientBuilder instead.
	 */
	public AuditClient(Config config) {
		super(config, serviceName);
		this.signer = null;
		this.pkInfo = null;
		publishedRoots = new HashMap<Integer, PublishedRoot>();
	}

	/**
	 * @deprecated use AuditClientBuilder instead.
	 */
	public AuditClient(Config config, String privateKeyFilename, Map<String, Object> pkInfo) {
		super(config, serviceName);
		this.signer = new LogSigner(privateKeyFilename);
		this.pkInfo = pkInfo;
		publishedRoots = new HashMap<Integer, PublishedRoot>();
	}

	/**
	 * @deprecated use AuditClientBuilder instead.
	 */
	public AuditClient(Config config, String privateKeyFilename) {
		super(config, serviceName);
		this.signer = new LogSigner(privateKeyFilename);
		publishedRoots = new HashMap<Integer, PublishedRoot>();
	}

	protected AuditClient(AuditClientBuilder builder) {
		super(builder.config, serviceName);
		if (builder.privateKeyFilename != null) {
			this.signer = new LogSigner(builder.privateKeyFilename);
		} else {
			this.signer = null;
		}
		this.tenantID = builder.tenantID;
		this.pkInfo = builder.pkInfo;
		publishedRoots = new HashMap<Integer, PublishedRoot>();
	}

	private LogResponse logPost(IEvent event, Boolean verbose, String signature, String publicKey, boolean verify)
		throws PangeaException, PangeaAPIException {
		String prevRoot = null;
		if (verify) {
			verbose = true;
			prevRoot = this.prevUnpublishedRoot;
		}
		LogRequest request = new LogRequest(event, verbose, signature, publicKey, prevRoot);
		return doPost("/v1/log", request, LogResponse.class);
	}

	private <EventType extends IEvent> LogResponse doLog(
		IEvent event,
		Boolean signLocal,
		Boolean verbose,
		boolean verify,
		Class<EventType> eventType
	) throws PangeaException, PangeaAPIException {
		String signature = null;
		String publicKey = null;

		if (this.tenantID != null) {
			event.setTenantID(this.tenantID);
		}

		if (signLocal == true) {
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

		LogResponse response = logPost(event, verbose, signature, publicKey, verify);
		processLogResponse(response.getResult(), verify, eventType);
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
					Verification.verifyMembershipProof(newUnpublishedRoot, result.hash, result.membershipProof);
				if (result.consistencyProof != null && this.prevUnpublishedRoot != null) {
					ConsistencyProof conProof = Verification.decodeConsistencyProof(result.consistencyProof);
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
	 * @pangea.description Log an event to Audit Secure Log. By default does not sign event and verbose is left as server default
	 * @param event event to log
	 * @return LogResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * String msg = "Event's message";
	 *
	 * Event event = new Event(msg);
	 *
	 * LogResponse response = client.log(event);
	 * }
	 */
	public <EventType extends IEvent> LogResponse log(IEvent event, Class<EventType> eventType)
		throws PangeaException, PangeaAPIException {
		return doLog(event, false, null, false, eventType);
	}

	/**
	 * Log an entry - event, sign, verbose
	 * @pangea.description Log an event to Audit Secure Log. Can select sign event or not and verbosity of the response.
	 * @param event event to log
	 * @param signMode "Unsigned" or "Local"
	 * @param verbose true to more verbose response
	 * @param pkInfo additional information about public key. Will be ignored if signMode is UNSIGNED
	 * @return LogResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * String msg = "Event's message";
	 *
	 * Event event = new Event(msg);
	 *
	 * LogResponse response = client.log(event, "Local", true);
	 * }
	 */
	public <EventType extends IEvent> LogResponse log(
		IEvent event,
		Boolean signLocal,
		boolean verbose,
		boolean verify,
		Class<EventType> eventType
	) throws PangeaException, PangeaAPIException {
		return doLog(event, signLocal, verbose, verify, eventType);
	}

	private RootResponse rootPost(Integer treeSize) throws PangeaException, PangeaAPIException {
		RootRequest request = new RootRequest(treeSize);
		return doPost("/v1/root", request, RootResponse.class);
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
	 * Get root from Pangea Server
	 * @pangea.description Get root from three of treeSize from Pangea Server
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
			if (searchEvent.published) {
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
		SearchRequest input,
		Class<EventType> eventType,
		SearchConfig config
	) throws PangeaException, PangeaAPIException {
		return searchPost(input, eventType, config);
	}

	private <EventType extends IEvent> SearchResponse searchPost(
		SearchRequest request,
		Class<EventType> eventType,
		SearchConfig config
	) throws PangeaException, PangeaAPIException {
		SearchResponse response = doPost("/v1/search", request, SearchResponse.class);
		processSearchResult(response.getResult(), eventType, config);
		return response;
	}

	private <EventType extends IEvent> ResultsResponse resultPost(
		ResultRequest request,
		Class<EventType> eventType,
		SearchConfig config
	) throws PangeaException, PangeaAPIException {
		ResultsResponse response = doPost("/v1/results", request, ResultsResponse.class);
		processSearchResult(response.getResult(), eventType, config);
		return response;
	}

	/**
	 * Results
	 * @pangea.description Return result's page from search id.
	 * @param id A search results identifier returned by the search call. By default verify events and do not verify consistency.
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

	public static class AuditClientBuilder {

		String privateKeyFilename = null;
		String tenantID = null;
		Config config;
		Map<String, Object> pkInfo = null;

		AuditClientBuilder(Config config) {
			this.config = config;
		}

		public AuditClient build() {
			return new AuditClient(this);
		}

		public AuditClientBuilder withPrivateKey(String privateKeyFilename) {
			this.privateKeyFilename = privateKeyFilename;
			return this;
		}

		public AuditClientBuilder withTenantID(String tenantID) {
			this.tenantID = tenantID;
			return this;
		}

		public AuditClientBuilder withPkInfo(Map<String, Object> pkInfo) {
			this.pkInfo = pkInfo;
			return this;
		}
	}
}
