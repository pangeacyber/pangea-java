package cloud.pangeacyber.pangea;

import java.net.URI;
import java.time.Duration;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

public final class Config {
	// The Bearer token used to authenticate requests.
    String token;

    // Base domain for API requests.
    String domain;

    // Set to true to use plain http
    boolean insecure;

    // Timeout for connections
    Duration connectionTimeout;

    // Set to "local" to debug
    String enviroment;

    public Config(String token, String domain) {
        this.token = token;
        this.domain = domain;
        this.insecure = false;
        this.enviroment = "production";
        this.connectionTimeout = Duration.ofSeconds(20);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(String enviroment) {
        this.enviroment = enviroment;
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

        if(enviroment != "local"){
            b.append(serviceName);
            b.append('.');
        }

        b.append(domain);
        b.append(path);
        return URI.create(b.toString());
    }

    public static Config fromEnvironment(String serviceName) throws ConfigException{
        String tokenEnvVarName = "PANGEA_" + serviceName.toUpperCase() + "_TOKEN";
        tokenEnvVarName = tokenEnvVarName.replace('-', '_');
        String token = System.getenv(tokenEnvVarName);
        if(token == null || token.isEmpty()){
            throw new ConfigException("Need to set up " + tokenEnvVarName + " environment variable");
        }

        String domain = System.getenv("PANGEA_DOMAIN");
        if(domain == null || domain.isEmpty()){
            throw new ConfigException("Need to set up PANGEA_DOMAIN environment variable");
        }

        Config config = new Config(token, domain);
        return config;
    }
}
