package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public final class LogStreamEventData {

	@JsonProperty("date")
	private OffsetDateTime date;

	@JsonProperty("type")
	private String type;

	@JsonProperty("description")
	private String description;

	@JsonProperty("client_id")
	private String clientID;

	@JsonProperty("ip")
	private String ip;

	@JsonProperty("user_agent")
	private String userAgent;

	@JsonProperty("user_id")
	private String userID;

	@JsonProperty("connection")
	private String connection;

	@JsonProperty("connection_id")
	private String connectionID;

	@JsonProperty("strategy")
	private String strategy;

	@JsonProperty("strategy_type")
	private String strategyType;

	public OffsetDateTime getDate() {
		return date;
	}

	public void setDate(OffsetDateTime value) {
		this.date = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String value) {
		this.type = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String value) {
		this.clientID = value;
	}

	public String getIP() {
		return ip;
	}

	public void setIP(String value) {
		this.ip = value;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String value) {
		this.userAgent = value;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String value) {
		this.userID = value;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String value) {
		this.connection = value;
	}

	public String getConnectionID() {
		return connectionID;
	}

	public void setConnectionID(String value) {
		this.connectionID = value;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String value) {
		this.strategy = value;
	}

	public String getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(String value) {
		this.strategyType = value;
	}
}
