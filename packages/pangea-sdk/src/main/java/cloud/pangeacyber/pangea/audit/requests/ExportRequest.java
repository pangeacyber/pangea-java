package cloud.pangeacyber.pangea.audit.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.audit.models.DownloadFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	private ExportRequest(
		DownloadFormat format,
		String start,
		String end,
		String order,
		String orderBy,
		boolean verbose
	) {
		this.format = format;
		this.start = start;
		this.end = end;
		this.order = order;
		this.orderBy = orderBy;
		this.verbose = verbose;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public String getOrder() {
		return order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Boolean getVerbose() {
		return verbose;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private DownloadFormat format = null;
		private String order = null;
		private String orderBy = null;
		private String start = null;
		private String end = null;
		private Boolean verbose = null;

		public Builder() {}

		public Builder format(DownloadFormat format) {
			this.format = format;
			return this;
		}

		public Builder order(String order) {
			this.order = order;
			return this;
		}

		public Builder orderBy(String orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		public Builder start(String start) {
			this.start = start;
			return this;
		}

		public Builder end(String end) {
			this.end = end;
			return this;
		}

		public Builder verbose(Boolean verbose) {
			this.verbose = verbose;
			return this;
		}

		public ExportRequest build() {
			return new ExportRequest(format, start, end, order, orderBy, verbose);
		}
	}
}
