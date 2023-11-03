package cloud.pangeacyber.pangea.audit.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogCommonRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("prev_root")
	String prevRoot;

	public LogCommonRequest(Boolean verbose, String prevRoot) {
		this.verbose = verbose;
		this.prevRoot = prevRoot;
	}
}
