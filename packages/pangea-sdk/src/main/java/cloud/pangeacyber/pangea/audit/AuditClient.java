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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.networknt.schema.InputFormat;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.ValidationMessage;
import io.swagger.util.Json;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

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

	/** Name of the schema for Audit events in the OpenAPI spec. */
	private static final String AUDIT_EVENT_SCHEMA_NAME = "AuditEvent";

	/** JSON schema factory. */
	private static final JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(VersionFlag.V202012);

	LogSigner signer;
	Map<Integer, PublishedRoot> publishedRoots;
	boolean allowServerRoots = true; // In case of Arweave failure, ask the server for the roots
	AtomicReference<String> prevUnpublishedRoot = new AtomicReference<>();
	Map<String, Object> pkInfo = null;
	String tenantID = null;
	Class<?> customSchemaClass = null;

	/** Cached JSON schema for validating events. */
	private JsonSchema eventSchema = null;

	/**
	 * Whether or not to validate events against the OpenAPI schema before
	 * sending them to Secure Audit Log.
	 */
	private boolean schemaValidation = false;

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
		this.schemaValidation = builder.schemaValidation;

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

		/**
		 * Whether or not to validate events against the OpenAPI schema before
		 * sending them to Secure Audit Log.
		 */
		private boolean schemaValidation = false;

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

		/**
		 * Whether or not to validate events against the OpenAPI schema before
		 * sending them to Secure Audit Log.
		 */
		public Builder withSchemaValidation(boolean schemaValidation) {
			this.schemaValidation = schemaValidation;
			return this;
		}
	}

	/**
	 * Fetch the OpenAPI spec.
	 *
	 * @param request Request body.
	 * @return OpenAPI spec.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 */
	private String openapi(BaseRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/openapi.json", request);
	}

	/**
	 * Validates an event against the schema from the OpenAPI spec.
	 *
	 * @throws SchemaValidationException Thrown if the event does not validate against the OpenAPI spec.
	 * @throws PangeaException Thrown if an error occurs during fetching the OpenAPI spec.
	 * @throws PangeaAPIException Thrown if fetching the OpenAPI spec returns an error response.
	 */
	private void validateEventSchema(LogCommonRequest request, IEvent event)
		throws PangeaException, PangeaAPIException {
		validateEventSchema(request, Collections.singleton(event));
	}

	/**
	 * Validates events against the schema from the OpenAPI spec.
	 *
	 * @throws SchemaValidationException Thrown on the first event that does not validate against the OpenAPI spec.
	 * @throws PangeaException Thrown if an error occurs during fetching the OpenAPI spec.
	 * @throws PangeaAPIException Thrown if fetching the OpenAPI spec returns an error response.
	 */
	private void validateEventSchema(LogCommonRequest request, Iterable<IEvent> events)
		throws PangeaException, PangeaAPIException {
		// Generate event schema if it hasn't been cached yet.
		if (eventSchema == null) {
			// Fetch OpenAPI spec.
			final var rawOpenApiSpec = openapi(request);

			// Parse the spec.
			final var parseOptions = new ParseOptions();
			parseOptions.setResolve(false);
			final var swaggerResult = new OpenAPIV3Parser().readContents(rawOpenApiSpec, null, parseOptions);
			final var openApiSpec = swaggerResult.getOpenAPI();

			// Get the JSON schema for events.
			final var schema = openApiSpec.getComponents().getSchemas().get(AUDIT_EVENT_SCHEMA_NAME);
			JsonNode schemaNode;
			try {
				// Serialize with swagger-parser's `ObjectMapper` instead of
				// `BaseClient`'s so that `null` values are not included as
				// `NullNode`s, because those will trip up json-schema-validator.
				schemaNode = objectMapper.readTree(Json.mapper().writeValueAsString(schema));
			} catch (JsonProcessingException error) {
				throw new SchemaValidationException("Failed to serialize or deserialize event schema.", error);
			}
			eventSchema = jsonSchemaFactory.getSchema(schemaNode);
		}

		for (var event : events) {
			Set<ValidationMessage> validationMessages;
			try {
				validationMessages =
					eventSchema.validate(
						objectMapper.writeValueAsString(event),
						InputFormat.JSON,
						executionContext -> executionContext.getExecutionConfig().setFormatAssertionsEnabled(true)
					);
			} catch (JsonProcessingException error) {
				throw new SchemaValidationException("Failed to serialize event.", error);
			}

			if (!validationMessages.isEmpty()) {
				// Clear the cached schema on a validation error just in case a
				// custom schema has changed server-side.
				eventSchema = null;

				throw new SchemaValidationException(validationMessages);
			}
		}
	}

	private LogRequest getLogRequest(LogEvent event, Boolean verbose, boolean verify, String prevRoot) {
		if (!verify) {
			prevRoot = null;
		}
		return new LogRequest(event, verbose, prevRoot);
	}

	private LogResponse doLog(IEvent event, LogConfig config) throws PangeaException, PangeaAPIException {
		LogEvent logEvent = getLogEvent(event, config);
		String prevRoot = this.prevUnpublishedRoot.get();
		final var logRequest = getLogRequest(logEvent, config.getVerbose(), config.getVerify(), prevRoot);

		if (this.schemaValidation) {
			this.validateEventSchema(logRequest, event);
		}

		LogResponse response = post("/v1/log", logRequest, LogResponse.class);
		processLogResult(response.getResult(), config.getVerify(), prevRoot);
		return response;
	}

	private LogBulkResponse doLogBulk(Iterable<IEvent> events, LogConfig config)
		throws PangeaException, PangeaAPIException {
		final var logRequest = new LogBulkRequest(getLogEvents(events, config), config.getVerbose());

		if (this.schemaValidation) {
			this.validateEventSchema(logRequest, events);
		}

		LogBulkResponse response = post("/v2/log", logRequest, LogBulkResponse.class);

		if (response.getResult() != null) {
			for (LogResult result : response.getResult().getResults()) {
				processLogResult(result, config.getVerify(), null);
			}
		}
		return response;
	}

	private LogBulkResponse doLogBulkAsync(Iterable<IEvent> events, LogConfig config)
		throws PangeaException, PangeaAPIException {
		final var logRequest = new LogBulkRequest(getLogEvents(events, config), config.getVerbose());

		if (this.schemaValidation) {
			this.validateEventSchema(logRequest, events);
		}

		LogBulkResponse response;
		try {
			response =
				post(
					"/v2/log_async",
					logRequest,
					LogBulkResponse.class,
					new PostConfig.Builder().pollResult(false).build()
				);
		} catch (AcceptedRequestException e) {
			return new LogBulkResponse(new Response<LogBulkResult>(e.getResponse(), e.getAcceptedResult()));
		}

		if (response.getResult() != null) {
			for (LogResult result : response.getResult().getResults()) {
				processLogResult(result, false, null);
			}
		}
		return response;
	}

	private ArrayList<LogEvent> getLogEvents(Iterable<IEvent> events, LogConfig config) throws PangeaException {
		final var logEvents = new ArrayList<LogEvent>();
		for (IEvent event : events) {
			logEvents.add(getLogEvent(event, config));
		}
		return logEvents;
	}

	private LogEvent getLogEvent(IEvent event, LogConfig config) throws PangeaException {
		String signature = null;
		String publicKey = null;

		if (this.tenantID != null) {
			event.setTenantID(this.tenantID);
		}

		if (config.getSignLocal()) {
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

	private void processLogResult(LogResult result, boolean verify, String prevRoot)
		throws VerificationFailed, PangeaException {
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
				if (result.getConsistencyProof() != null && prevRoot != null) {
					ConsistencyProof conProof = Verification.decodeConsistencyProof(result.getConsistencyProof());
					result.consistencyVerification =
						Verification.verifyConsistencyProof(newUnpublishedRoot, prevRoot, conProof);
				}
			}
		}

		if (newUnpublishedRoot != null) {
			this.prevUnpublishedRoot.set(newUnpublishedRoot);
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
		return doLogBulk(Arrays.asList(events), config);
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
		return doLogBulkAsync(Arrays.asList(events), config);
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

	/**
	 * Download search results
	 * @pangea.description Get all search results as a compressed (gzip) CSV file.
	 * @pangea.operationId audit_post_v1_download_results
	 * @param request Request parameters.
	 * @return URL where search results can be downloaded.
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * var response = client.downloadResults(
	 *     new DownloadRequest.Builder("pas_[...]")
	 *         .format(DownloadFormat.CSV)
	 *         .build()
	 * );
	 * }
	 */
	public DownloadResponse downloadResults(DownloadRequest request) throws PangeaException, PangeaAPIException {
		if (request.getRequestID() == null && request.getResultID() == null) {
			throw new IllegalArgumentException("must pass a request ID or a result ID");
		}

		return post("/v1/download_results", request, DownloadResponse.class);
	}

	/**
	 * Log streaming endpoint
	 * @pangea.description This API allows 3rd party vendors (like Auth0) to
	 * stream events to this endpoint where the structure of the payload varies
	 * across different vendors.
	 * @pangea.operationId audit_post_v1_log_stream
	 * @param request Event data. The exact schema of this will vary by vendor.
	 * @return A Pangea response.
	 * @throws PangeaException Thrown if an error occurs during the operation.
	 * @throws PangeaAPIException Thrown if the API returns an error response.
	 * @pangea.code
	 * {@code
	 * // Extend `BaseRequest` and model what the streaming data looks like.
	 * public final class LogStreamRequest extends BaseRequest {
	 * 	@JsonProperty("logs")
	 * 	private List<LogStreamEvent> logs;
	 * }
	 *
	 * // Then later on, log it like so:
	 * final var input = new LogStreamRequest();
	 * final var response = await client.logStream(input);
	 * }
	 */
	public Response<Void> logStream(BaseRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/log_stream", request, new TypeReference<Response<Void>>() {});
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
				if (searchEvent.getConsistencyVerification() == EventVerification.FAILED) {
					// try to verify the consistency proof obtained from Pangea (just the proof, not the root hash)
					this.fixConsistencyProof(searchEvent.leafIndex + 1);
					searchEvent.verifyConsistency(publishedRoots);
				}
			}
		}
	}

	private void fixConsistencyProof(int treeSize) {
		// on very rare occasions, the consistency proof in Arweave may be wrong
		// override it with the proof from pangea (not the root hash, just the proof)

		// on error, do nothing (the proof will fail)

		PublishedRoot arweaveRoot = this.publishedRoots.get(treeSize);
		if (arweaveRoot == null) return;

		// get the root from Pangea
		RootResponse resp;
		try {
			resp = this.getRoot(treeSize);
		} catch (PangeaException | PangeaAPIException e) {
			return;
		}

		Root pangeaRoot = resp.getResult().getRoot();

		// compare the hash
		if (!pangeaRoot.getRootHash().equals(arweaveRoot.getRootHash())) return;

		// override proof
		this.publishedRoots.put(
				treeSize,
				new PublishedRoot(
					pangeaRoot.getSize(),
					pangeaRoot.getRootHash(),
					pangeaRoot.getPublishedAt(),
					pangeaRoot.getConsistencyProof(),
					"pangea"
				)
			);
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

	/**
	 * Export from the audit log
	 * @pangea.description Bulk export of data from the Secure Audit Log, with
	 * optional filtering.
	 * @pangea.operationId audit_post_v1_export
	 * @param request Request parameters.
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * var response = client.export(ExportRequest.builder().verbose(true).build());
	 * }
	 */
	public Response<Void> export(ExportRequest request) throws PangeaException, PangeaAPIException {
		try {
			return post(
				"/v1/export",
				request,
				new TypeReference<Response<Void>>() {},
				new PostConfig.Builder().pollResult(false).build()
			);
		} catch (AcceptedRequestException e) {
			return new Response<Void>(e.getResponse(), e.getAcceptedResult());
		}
	}
}
