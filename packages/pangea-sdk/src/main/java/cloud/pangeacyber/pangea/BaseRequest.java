package cloud.pangeacyber.pangea;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("config_id")
	String configID;

	public BaseRequest() {}

	String getConfigID() {
		return this.configID;
	}

	void setConfigID(String configID) {
		this.configID = configID;
	}
}
