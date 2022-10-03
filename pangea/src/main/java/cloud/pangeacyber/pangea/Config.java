package cloud.pangeacyber.pangea;

import java.net.URI;
import java.time.Duration;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

public final class Config {
	// The Bearer token used to authenticate requests.
    String token;

    // The Config ID token of the service.
    String configId;

    // Base domain for API requests.
    String domain;

    // Set to true to use plain http
    boolean insecure;

    // Timeout for connections
    Duration connectionTimeout;

    public Config(String token, String configId, String domain) {
        this.token = token;
        this.configId = configId;
        this.domain = domain;

        this.insecure = false;
        this.connectionTimeout = Duration.ofSeconds(20);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isInsecure() {
        return insecure;
    }

    public void setInsecure(boolean insecure) {
        this.insecure = insecure;
    }

    public Duration getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    URI getServiceUrl(String serviceName, String path) {
        StringBuilder b = new StringBuilder();
        b.append(insecure ? "http://" : "https://");
        b.append(serviceName);
        b.append('.');
        b.append(domain);
        b.append(path);
        return URI.create(b.toString());
    }

    public String getServiceIdHeaderName(String serviceName) {
        return "X-Pangea-" + Character.toTitleCase(serviceName.charAt(0)) + serviceName.substring(1) + "-Config-Id";
    }

    public static Config fromEnvironment(String serviceName) throws ConfigException{
        String token = System.getenv("PANGEA_TOKEN");
        if(token == null || token.isEmpty()){
            throw new ConfigException("Need to set up PANGEA_TOKEN environment variable");
        }

        String configId = System.getenv(String.format("PANGEA_%s_CONFIG_ID", serviceName.toUpperCase()));
        if(configId == null || configId.isEmpty()){
            String envVarName = String.format("PANGEA_%s_CONFIG_ID", serviceName.toUpperCase());
            throw new ConfigException(String.format("Need to set up %s environment variable", envVarName));
        }

        String domain = System.getenv("PANGEA_DOMAIN");
        if(domain == null || domain.isEmpty()){
            throw new ConfigException("Need to set up PANGEA_DOMAIN environment variable");
        }

        Config config = new Config(token, configId, domain);
        return config;
    }
}
