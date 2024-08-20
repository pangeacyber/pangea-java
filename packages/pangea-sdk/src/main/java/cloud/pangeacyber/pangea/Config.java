package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import java.net.URI;
import java.time.Duration;

public final class Config {

	// The Bearer token used to authenticate requests.
	String token;

	// Base domain for API requests.
	String domain;

	// Project config id need for multi-config projects
	/**
	 * @deprecated set configID in service builder.
	 */
	@Deprecated
	String configID;

	// Set to true to use plain http
	boolean insecure;

	// Timeout for connections
	Duration connectionTimeout;

	// Set to "local" to debug
	String enviroment;

	// Extra custom user-agent to send on requests
	String customUserAgent;

	// Enable queued request retry support
	boolean queuedRetryEnabled;

	// Timeout used to poll results after 202 (in secs)
	long pollResultTimeout;

	/** Maximum number of allowed retries on server errors. */
	final int maxRetries;

	/** Retry interval between subsequent requests. */
	final Duration retryInterval;

	/** Maximum number of total HTTP connections. */
	private final int maxTotalConnections;

	/** Maximum number of HTTP requests per route. */
	private final int maxConnectionsPerRoute;

	protected Config(Builder builder) {
		this.domain = builder.domain;
		this.token = builder.token;
		this.enviroment = builder.enviroment;
		this.insecure = builder.insecure;
		this.connectionTimeout = builder.connectionTimeout;
		this.customUserAgent = builder.customUserAgent;
		this.queuedRetryEnabled = builder.queuedRetryEnabled;
		this.pollResultTimeout = builder.pollResultTimeout;
		this.configID = builder.configID;
		this.maxRetries = builder.maxRetries;
		this.retryInterval = builder.retryInterval;
		this.maxTotalConnections = builder.maxTotalConnections;
		this.maxConnectionsPerRoute = builder.maxConnectionsPerRoute;
	}

	public String getToken() {
		return token;
	}

	public String getDomain() {
		return domain;
	}

	public String getEnviroment() {
		return enviroment;
	}

	public boolean isInsecure() {
		return insecure;
	}

	public Duration getConnectionTimeout() {
		return connectionTimeout;
	}

	public String getCustomUserAgent() {
		return customUserAgent;
	}

	public boolean isQueuedRetryEnabled() {
		return queuedRetryEnabled;
	}

	public long getPollResultTimeout() {
		return pollResultTimeout;
	}

	public String getConfigID() {
		return configID;
	}

	/** @return Maximum number of allowed retries on server errors. */
	public final int getMaxRetries() {
		return this.maxRetries;
	}

	/** @return Retry interval between subsequent requests. */
	public final Duration getRetryInterval() {
		return this.retryInterval;
	}

	/** @return Maximum number of total HTTP connections. */
	public final int getMaxTotalConnections() {
		return this.maxTotalConnections;
	}

	/** @return Maximum number of HTTP requests per route. */
	public final int getMaxConnectionsPerRoute() {
		return this.maxConnectionsPerRoute;
	}

	URI getServiceUrl(String serviceName, String path) {
		StringBuilder b = new StringBuilder();
		if (domain.startsWith("http://") || domain.startsWith("https://")) {
			// url domain
			b.append(domain);
		} else {
			b.append(insecure ? "http://" : "https://");
			if (enviroment != "local") {
				b.append(serviceName);
				b.append('.');
			}
			b.append(domain);
		}
		b.append(path);
		return URI.create(b.toString());
	}

	public static Config fromEnvironment(String serviceName) throws ConfigException {
		String tokenEnvVarName = "PANGEA_" + serviceName.toUpperCase() + "_TOKEN";
		tokenEnvVarName = tokenEnvVarName.replace('-', '_');
		String token = System.getenv(tokenEnvVarName);
		if (token == null || token.isEmpty()) {
			throw new ConfigException("Need to set up " + tokenEnvVarName + " environment variable");
		}

		String domain = System.getenv("PANGEA_DOMAIN");
		if (domain == null || domain.isEmpty()) {
			throw new ConfigException("Need to set up PANGEA_DOMAIN environment variable");
		}

		Config config = new Config.Builder(token, domain).customUserAgent("test").build();
		return config;
	}

	public static Config fromIntegrationEnvironment(TestEnvironment environment) throws ConfigException {
		String token = getTestToken(environment);
		String domain = getTestDomain(environment);
		Config config = new Config.Builder(token, domain).customUserAgent("test").build();
		return config;
	}

	public static Config fromVaultIntegrationEnvironment(TestEnvironment environment) throws ConfigException {
		String token = getVaultSignatureTestToken(environment);
		String domain = getTestDomain(environment);
		Config config = new Config.Builder(token, domain).customUserAgent("test").build();
		return config;
	}

	public static Config fromCustomSchemaIntegrationEnvironment(TestEnvironment environment) throws ConfigException {
		String token = getCustomSchemaTestToken(environment);
		String domain = getTestDomain(environment);
		Config config = new Config.Builder(token, domain).customUserAgent("test").build();
		return config;
	}

	private static String loadEnvVar(String envVarName) throws ConfigException {
		String value = System.getenv(envVarName);
		if (value == null || value.isEmpty()) {
			throw new ConfigException(envVarName + " environment variable need to be set");
		}
		return value;
	}

	public static String getTestToken(TestEnvironment environment) throws ConfigException {
		String envVarName = "PANGEA_INTEGRATION_TOKEN_" + environment.toString();
		return loadEnvVar(envVarName);
	}

	public static String getVaultSignatureTestToken(TestEnvironment environment) throws ConfigException {
		String envVarName = "PANGEA_INTEGRATION_VAULT_TOKEN_" + environment.toString();
		return loadEnvVar(envVarName);
	}

	public static String getMultiConfigTestToken(TestEnvironment environment) throws ConfigException {
		String envVarName = "PANGEA_INTEGRATION_MULTI_CONFIG_TOKEN_" + environment.toString();
		return loadEnvVar(envVarName);
	}

	public static String getConfigID(TestEnvironment environment, String service, int configNumber)
		throws ConfigException {
		String envVarName = String.format(
			"PANGEA_%s_CONFIG_ID_%d_%s",
			service.toUpperCase(),
			configNumber,
			environment.toString()
		);
		return loadEnvVar(envVarName);
	}

	public static String getCustomSchemaTestToken(TestEnvironment environment) throws ConfigException {
		String tokenEnvVarName = "PANGEA_INTEGRATION_CUSTOM_SCHEMA_TOKEN_" + environment.toString();
		String token = System.getenv(tokenEnvVarName);
		if (token == null || token.isEmpty()) {
			throw new ConfigException("Need to set up " + tokenEnvVarName + " environment variable");
		}
		return token;
	}

	public static String getTestDomain(TestEnvironment environment) throws ConfigException {
		String envVarName = "PANGEA_INTEGRATION_DOMAIN_" + environment.toString();
		return loadEnvVar(envVarName);
	}

	public void setQueuedRetryEnabled(boolean queuedRetryEnabled) {
		this.queuedRetryEnabled = queuedRetryEnabled;
	}

	public void setPollResultTimeout(long pollResultTimeout) {
		this.pollResultTimeout = pollResultTimeout;
	}

	public static class Builder {

		String token;
		String domain;
		boolean insecure = false;
		Duration connectionTimeout;
		String enviroment;
		String customUserAgent;
		boolean queuedRetryEnabled;
		long pollResultTimeout;
		String configID;
		int maxRetries;
		Duration retryInterval;

		/** Maximum number of total HTTP connections. */
		int maxTotalConnections = 50;

		/** Maximum number of HTTP requests per route. */
		int maxConnectionsPerRoute = 50;

		public Builder(String token, String domain) {
			this.token = token;
			this.domain = domain;
			this.insecure = false;
			this.enviroment = "production";
			this.connectionTimeout = Duration.ofSeconds(20);
			this.customUserAgent = "";
			this.queuedRetryEnabled = true;
			this.pollResultTimeout = 240;
			this.maxRetries = 3;
			this.retryInterval = Duration.ofSeconds(5);
		}

		public Builder queuedRetryEnabled(boolean queuedRetryEnabled) {
			this.queuedRetryEnabled = queuedRetryEnabled;
			return this;
		}

		public Builder pollResultTimeout(long pollResultTimeout) {
			this.pollResultTimeout = pollResultTimeout;
			return this;
		}

		public Builder insecure(boolean insecure) {
			this.insecure = insecure;
			return this;
		}

		public Builder connectionTimeout(Duration connectionTimeout) {
			this.connectionTimeout = connectionTimeout;
			return this;
		}

		public Builder enviroment(String enviroment) {
			this.enviroment = enviroment;
			return this;
		}

		public Builder customUserAgent(String customUserAgent) {
			this.customUserAgent = customUserAgent;
			return this;
		}

		/**
		 * @deprecated set configID in service builder.
		 */
		@Deprecated
		public Builder configID(String configID) {
			this.configID = configID;
			return this;
		}

		/** Set the maximum number of allowed retries on server errors. */
		public Builder maxRetries(int maxRetries) {
			this.maxRetries = maxRetries;
			return this;
		}

		/** Set the retry interval between subsequent requests. */
		public Builder retryInterval(Duration retryInterval) {
			this.retryInterval = retryInterval;
			return this;
		}

		/** Set the maximum number of total HTTP connections. */
		public Builder maxTotalConnections(int maxTotalConnections) {
			this.maxTotalConnections = maxTotalConnections;
			return this;
		}

		/** Set the maximum number of HTTP requests per route. */
		public Builder maxConnectionsPerRoute(int maxConnectionsPerRoute) {
			this.maxConnectionsPerRoute = maxConnectionsPerRoute;
			return this;
		}

		public Config build() {
			return new Config(this);
		}
	}
}
