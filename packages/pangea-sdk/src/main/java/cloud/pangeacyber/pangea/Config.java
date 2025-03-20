package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import java.time.Duration;

@lombok.Builder
@lombok.Data
public final class Config {

	/** The Bearer token used to authenticate requests. */
	@lombok.NonNull
	String token;

	/** Used to set Pangea domain and protocol and port if needed. It should include the SERVICE_NAME placeholder. */
	@lombok.NonNull
	String baseURLTemplate;

	/** Set to true to use plain HTTP */
	@lombok.Builder.Default
	boolean insecure = false;

	/** Timeout for connections */
	@lombok.Builder.Default
	Duration connectionTimeout = Duration.ofSeconds(20);

	/**
	 * Pangea environment. If set to "local", then `domain` must be the full
	 * host (i.e., hostname and port) for the Pangea service that this `Config`
	 * will be used for.
	 */
	@lombok.Builder.Default
	String environment = "production";

	/** @deprecated Use `environment` instead. */
	@lombok.Builder.Default
	@Deprecated
	String enviroment = "production";

	/** Extra custom user-agent to send on requests */
	@lombok.Builder.Default
	String customUserAgent = "";

	/** Enable queued request retry support */
	@lombok.Builder.Default
	boolean queuedRetryEnabled = true;

	/** Timeout used to poll results after 202 (in seconds) */
	@lombok.Builder.Default
	long pollResultTimeout = 240;

	/** Maximum number of allowed retries on server errors. */
	@lombok.Builder.Default
	int maxRetries = 3;

	/** Retry interval between subsequent requests. */
	@lombok.Builder.Default
	Duration retryInterval = Duration.ofSeconds(5);

	/** Maximum number of total HTTP connections. */
	@lombok.Builder.Default
	int maxTotalConnections = 50;

	/** Maximum number of HTTP requests per route. */
	@lombok.Builder.Default
	int maxConnectionsPerRoute = 50;

	public static Config fromEnvironment(String serviceName) throws ConfigException {
		String tokenEnvVarName = "PANGEA_" + serviceName.toUpperCase() + "_TOKEN";
		tokenEnvVarName = tokenEnvVarName.replace('-', '_');
		String token = System.getenv(tokenEnvVarName);
		if (token == null || token.isEmpty()) {
			throw new ConfigException("Need to set up " + tokenEnvVarName + " environment variable");
		}

		String urlTemplate = System.getenv("PANGEA_URL_TEMPLATE");
		if (urlTemplate == null || urlTemplate.isEmpty()) {
			throw new ConfigException("Need to set up PANGEA_URL_TEMPLATE environment variable");
		}

		Config config = Config.builder().token(token).baseURLTemplate(urlTemplate).customUserAgent("test").build();
		return config;
	}

	public static Config fromIntegrationEnvironment(TestEnvironment environment) throws ConfigException {
		String token = getTestToken(environment);
		String urlTemplate = getTestURLTemplate(environment);
		Config config = Config.builder().token(token).baseURLTemplate(urlTemplate).customUserAgent("test").build();
		return config;
	}

	public static Config fromVaultIntegrationEnvironment(TestEnvironment environment) throws ConfigException {
		String token = getVaultSignatureTestToken(environment);
		String urlTemplate = getTestURLTemplate(environment);
		Config config = Config.builder().token(token).baseURLTemplate(urlTemplate).customUserAgent("test").build();
		return config;
	}

	public static Config fromCustomSchemaIntegrationEnvironment(TestEnvironment environment) throws ConfigException {
		String token = getCustomSchemaTestToken(environment);
		String urlTemplate = getTestURLTemplate(environment);
		Config config = Config.builder().token(token).baseURLTemplate(urlTemplate).customUserAgent("test").build();
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

	public static String getTestURLTemplate(TestEnvironment environment) throws ConfigException {
		String envVarName = "PANGEA_INTEGRATION_URL_TEMPLATE_" + environment.toString();
		return loadEnvVar(envVarName);
	}
}
