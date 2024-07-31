package cloud.pangeacyber.pangea.share.results;

import cloud.pangeacyber.pangea.share.models.Bucket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class BucketsResult {

	/** A list of available buckets. */
	@JsonProperty("buckets")
	private List<Bucket> buckets;

	public BucketsResult() {}

	/** A list of available buckets. */
	public List<Bucket> getBuckets() {
		return this.buckets;
	}
}
