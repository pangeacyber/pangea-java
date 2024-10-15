package cloud.pangeacyber.pangea.audit.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.audit.models.DownloadFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
@JsonInclude(Include.NON_NULL)
public final class ExportRequest extends BaseRequest {

	/** Format for the records. */
	@JsonProperty("format")
	private final DownloadFormat format;

	/** The start of the time range to perform the search on. */
	@JsonProperty("start")
	private final String start;

	/**
	 * The end of the time range to perform the search on. If omitted, then all
	 * records up to the latest will be searched.
	 */
	@JsonProperty("end")
	private final String end;

	/** Specify the sort order of the response. */
	@JsonProperty("order")
	private final String order;

	/** Name of column to sort the results by. */
	@JsonProperty("order_by")
	private final String orderBy;

	/**
	 * Whether or not to include the root hash of the tree and the membership
	 * proof for each record.
	 */
	@JsonProperty("verbose")
	private boolean verbose;
}
