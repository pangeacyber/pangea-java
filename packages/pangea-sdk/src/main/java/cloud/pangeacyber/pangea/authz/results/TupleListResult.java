package cloud.pangeacyber.pangea.authz.results;

import cloud.pangeacyber.pangea.authz.models.Tuple;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TupleListResult {

	@JsonProperty("tuples")
	private Tuple[] tuples;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	private String last;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("count")
	private int count;

	public TupleListResult() {}

	public Tuple[] getTuples() {
		return tuples;
	}

	public String getLast() {
		return last;
	}

	public int getCount() {
		return count;
	}
}
