package cloud.pangeacyber.pangea.authn.models;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ExpirePasswordRequest extends BaseRequest {

	/** The identity of a user or a service. */
	@JsonProperty("id")
	String id;

	public ExpirePasswordRequest(String id) {
		this.id = id;
	}
}
