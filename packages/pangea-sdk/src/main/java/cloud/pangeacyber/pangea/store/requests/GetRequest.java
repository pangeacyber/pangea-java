package cloud.pangeacyber.pangea.store.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.TransferMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("path")
	String path;

	protected GetRequest(Builder builder) {
		this.id = builder.id;
		this.path = builder.path;
		this.setTransferMethod(builder.transferMethod);
	}

	public static class Builder {

		String id;
		String path;
		TransferMethod transferMethod;

		public Builder() {
			// Empty constructor
		}

		public GetRequest build() {
			return new GetRequest(this);
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder path(String path) {
			this.path = path;
			return this;
		}

		public Builder transferMethod(TransferMethod transferMethod) {
			this.transferMethod = transferMethod;
			return this;
		}
	}
}
