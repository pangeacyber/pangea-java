package cloud.pangeacyber.pangea;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("config_id")
	String configID = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transfer_method")
	TransferMethod transferMethod = null;

	public BaseRequest() {}

	String getConfigID() {
		return this.configID;
	}

	void setConfigID(String configID) {
		this.configID = configID;
	}

	//
	public TransferMethod getTransferMethod() {
		return transferMethod;
	}

	// This should be called in the derived class that really need it
	protected void setTransferMethod(TransferMethod transferMethod) {
		this.transferMethod = transferMethod;
	}
}
