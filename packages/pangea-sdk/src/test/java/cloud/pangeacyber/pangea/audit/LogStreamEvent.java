package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LogStreamEvent {

	@JsonProperty("log_id")
	private String logID;

	@JsonProperty("data")
	private LogStreamEventData data;

	public String getLogID() {
		return logID;
	}

	public void setLogID(String value) {
		this.logID = value;
	}

	public LogStreamEventData getData() {
		return data;
	}

	public void setData(LogStreamEventData value) {
		this.data = value;
	}
}
