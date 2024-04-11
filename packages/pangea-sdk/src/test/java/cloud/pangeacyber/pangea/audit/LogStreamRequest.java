package cloud.pangeacyber.pangea.audit;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public final class LogStreamRequest extends BaseRequest {

	@JsonProperty("logs")
	private List<LogStreamEvent> logs;

	public List<LogStreamEvent> getLogs() {
		return logs;
	}

	public void setLogs(List<LogStreamEvent> value) {
		this.logs = value;
	}
}
