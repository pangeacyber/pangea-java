package cloud.pangeacyber.pangea.audit.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultRequest extends BaseRequest {

	@JsonProperty("id")
	private String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("limit")
	private Integer limit;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("offset")
	private Integer offset;

	private ResultRequest(Builder builder) {
		this.id = builder.id;
		this.limit = builder.limit;
		this.offset = builder.offset;
	}

	public String getId() {
		return id;
	}

	public Integer getLimit() {
		return limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public static class Builder {

		private String id;
		private Integer limit;
		private Integer offset;

		public Builder(String id) {
			this.id = id;
		}

		public Builder limit(Integer limit) {
			this.limit = limit;
			return this;
		}

		public Builder offset(Integer offset) {
			this.offset = offset;
			return this;
		}

		public ResultRequest build() {
			return new ResultRequest(this);
		}
	}
}
