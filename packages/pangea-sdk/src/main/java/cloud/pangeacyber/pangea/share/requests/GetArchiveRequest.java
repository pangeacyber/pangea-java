package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.share.models.ArchiveFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GetArchiveRequest extends BaseRequest {

	@JsonProperty("ids")
	List<String> ids;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("format")
	ArchiveFormat format;

	protected GetArchiveRequest(Builder builder) {
		this.ids = builder.ids;
		this.format = builder.format;
		this.setTransferMethod(builder.transferMethod);
	}

	public static class Builder {

		List<String> ids;
		ArchiveFormat format;
		TransferMethod transferMethod;

		public Builder(List<String> ids) {
			this.ids = ids;
		}

		public GetArchiveRequest build() {
			return new GetArchiveRequest(this);
		}

		public Builder format(ArchiveFormat format) {
			this.format = format;
			return this;
		}

		public Builder transferMethod(TransferMethod transferMethod) {
			this.transferMethod = transferMethod;
			return this;
		}
	}
}
