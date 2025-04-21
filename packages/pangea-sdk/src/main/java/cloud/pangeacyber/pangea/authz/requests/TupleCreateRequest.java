package cloud.pangeacyber.pangea.authz.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authz.models.Tuple;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Arrays;
import java.util.List;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
@Value
public final class TupleCreateRequest extends BaseRequest {

	@NonNull
	private List<Tuple> tuples;

	// Legacy alias.
	public static class Builder extends TupleCreateRequestBuilder<TupleCreateRequest, TupleCreateRequest.Builder> {

		public Builder(final Tuple[] tuples) {
			this.tuples(Arrays.asList(tuples));
		}

		@Override
		protected Builder self() {
			return this;
		}

		@Override
		public TupleCreateRequest build() {
			return new TupleCreateRequest(this);
		}
	}
}
