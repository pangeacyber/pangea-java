package cloud.pangeacyber.pangea.audit;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.PostConfig;
import cloud.pangeacyber.pangea.Response;
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
import java.util.ArrayList;
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

final class LogEvent {

	@JsonProperty("event")
	IEvent event;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("signature")
	String signature;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("public_key")
	String publicKey;

	public LogEvent(IEvent event, String signature, String publicKey) {
		this.event = event;
		this.signature = signature;
		this.publicKey = publicKey;
	}

	public IEvent getEvent() {
		return event;
	}

	public String getSignature() {
		return signature;
	}

	public String getPublicKey() {
		return publicKey;
	}
}

final class LogRequest extends LogCommonRequest {

	@JsonProperty("event")
	IEvent event;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("signature")
	String signature;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("public_key")
	String publicKey;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("prev_root")
	String prevRoot;

	public LogRequest(LogEvent event, Boolean verbose, String prevRoot) {
		super(verbose);
		this.event = event.getEvent();
		this.signature = event.getSignature();
		this.publicKey = event.getPublicKey();
		this.prevRoot = prevRoot;
	}
}

final class LogBulkRequest extends LogCommonRequest {

	@JsonProperty("events")
	ArrayList<LogEvent> events;

	public LogBulkRequest(ArrayList<LogEvent> events, Boolean verbose) {
		super(verbose);
		this.events = events;
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
	Class<?> customSchemaClass = null;

	public AuditClient(Builder builder) {
		super(builder, serviceName);
		if (builder.privateKeyFilename != null) {
			this.signer = new LogSigner(builder.privateKeyFilename);
		} else {
			this.signer = null;
		}
		this.tenantID = builder.tenantID;
		this.pkInfo = builder.pkInfo;
		publishedRoots = new HashMap<Integer, PublishedRoot>();
		this.customSchemaClass = builder.customSchemaClass;

		// FIXME: still support config id in PangeaConfig. Remove this code block when totally deprecated
		if (builder.configID != null && !builder.configID.isEmpty()) {
			setConfigID(builder.configID);
		} else if (this.config.getConfigID() != null && !this.config.getConfigID().isEmpty()) {
			setConfigID(this.config.getConfigID());
		}
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		String privateKeyFilename = null;
		String tenantID = null;
		Config config;
		Map<String, Object> pkInfo = null;
		String configID = null;
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

		public Builder withConfigID(String configID) {
			this.configID = configID;
			return this;
		}

		public <EventType extends IEvent> Builder withCustomSchema(Class<EventType> customSchemaClass) {
			this.customSchemaClass = customSchemaClass;
			return this;
		}
	}

	private LogRequest getLogRequest(LogEvent event, Boolean verbose, boolean verify) {
		String prevRoot = null;
		if (verify) {
			verbose = true;
			prevRoot = this.prevUnpublishedRoot;
		}
		return new LogRequest(event, verbose, prevRoot);
	}

	private LogResponse doLog(IEvent event, LogConfig config) throws PangeaException, PangeaAPIException {
		LogEvent logEvent = getLogEvent(event, config);
		LogResponse response = post(
			"/v1/log",
			getLogRequest(logEvent, config.getVerbose(), config.getVerify()),
			LogResponse.class
		);
		processLogResult(response.getResult(), config.getVerify());
		return response;
	}

	private LogBulkResponse doLogBulk(IEvent[] events, LogConfig config) throws PangeaException, PangeaAPIException {
		LogBulkResponse response = post(
			"/v2/log",
			new LogBulkRequest(getLogEvents(events, config), config.getVerbose()),
			LogBulkResponse.class
		);

		if (response.getResult() != null) {
			for (LogResult result : response.getResult().getResults()) {
				processLogResult(result, config.getVerify());
			}
		}
		return response;
	}

	private LogBulkResponse doLogBulkAsync(IEvent[] events, LogConfig config)
		throws PangeaException, PangeaAPIException {
		LogBulkResponse response;
		try {
			response =
				post(
					"/v2/log_async",
					new LogBulkRequest(getLogEvents(events, config), config.getVerbose()),
					LogBulkResponse.class,
					new PostConfig.Builder().pollResult(false).build()
				);
		} catch (AcceptedRequestException e) {
			return new LogBulkResponse(new Response<LogBulkResult>(e.getResponse(), e.getAcceptedResult()));
		}

		if (response.getResult() != null) {
			for (LogResult result : response.getResult().getResults()) {
				processLogResult(result, false);
			}
		}
		return response;
	}

	private ArrayList<LogEvent> getLogEvents(IEvent[] events, LogConfig config) throws PangeaException {
		ArrayList<LogEvent> logEvents = new ArrayList<LogEvent>();
		for (IEvent event : events) {
			LogEvent logEvent = getLogEvent(event, config);
			logEvents.add(logEvent);
		}
		return logEvents;
	}

	private LogEvent getLogEvent(IEvent event, LogConfig config) throws PangeaException {
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

		return new LogEvent(event, signature, publicKey);
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

	private void processLogResult(LogResult result, boolean verify) throws VerificationFailed, PangeaException {
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

	/**
	 * Log multiple entries
	 * @pangea.description Create multiple log entries in the Secure Audit Log.
	 * @pangea.operationId audit_post_v2_log
	 * @param events events to log
	 * @param config
	 * @return LogBulkResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * StandardEvent event = new StandardEvent
	 * 	.Builder("Hello, World!")
	 * 	.action("Login")
	 * 	.actor("Terminal")
	 * 	.build();
	 * StandardEvent[] events = {event};
	 * LogConfig config = new LogConfig.Builder().build();
	 *
	 * LogBulkResponse response = client.logBulk(events, config);
	 * }
	 */
	public LogBulkResponse logBulk(IEvent[] events, LogConfig config) throws PangeaException, PangeaAPIException {
		if (config == null) {
			config = new LogConfig.Builder().build();
		}
		return doLogBulk(events, config);
	}

	/**
	 * Log multiple entries asynchronously
	 * @pangea.description Asynchronously create multiple log entries in the Secure Audit Log.
	 * @pangea.operationId audit_post_v2_log_async
	 * @param events events to log
	 * @param config
	 * @return LogBulkResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * StandardEvent event = new StandardEvent
	 * 	.Builder("Hello, World!")
	 * 	.action("Login")
	 * 	.actor("Terminal")
	 * 	.build();
	 * StandardEvent[] events = {event};
	 * LogConfig config = new LogConfig.Builder().build();
	 *
	 * LogBulkResponse response = client.logBulkAsync(events, config);
	 * }
	 */
	public LogBulkResponse logBulkAsync(IEvent[] events, LogConfig config) throws PangeaException, PangeaAPIException {
		if (config == null) {
			config = new LogConfig.Builder().build();
		}
		return doLogBulkAsync(events, config);
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

	// FIXME: Docs
	public DownloadResponse DownloadResults(DownloadRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/download_results", request, DownloadResponse.class);
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
