package cloud.pangeacyber.pangea.authz.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authz.models.Tuple;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TupleDeleteRequest extends BaseRequest {

	@JsonProperty("tuples")
	private Tuple[] tuples;

	private TupleDeleteRequest(Builder builder) {
		this.tuples = builder.tuples;
	}

	public Tuple[] getTuples() {
		return tuples;
	}

	public static class Builder {

		private Tuple[] tuples;

		public Builder(Tuple[] tuples) {
			this.tuples = tuples;
		}

		public TupleDeleteRequest build() {
			return new TupleDeleteRequest(this);
		}
	}
}
