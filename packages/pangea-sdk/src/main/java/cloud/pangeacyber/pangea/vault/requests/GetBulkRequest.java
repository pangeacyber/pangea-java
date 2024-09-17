package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.ItemOrder;
import cloud.pangeacyber.pangea.vault.models.ItemOrderBy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
@JsonInclude(Include.NON_NULL)
public class GetBulkRequest extends BaseRequest {

	/** A set of filters to customize the search. */
	@Builder.Default
	@JsonProperty("filter")
	Map<String, String> filter = null;

	/** Maximum number of items in the response. */
	@Builder.Default
	@JsonProperty("size")
	Integer size = null;

	/** Ordering direction. */
	@Builder.Default
	@JsonProperty("order")
	ItemOrder order = null;

	/** Property used to order the results. */
	@Builder.Default
	@JsonProperty("order_by")
	ItemOrderBy orderBy = null;

	/**
	 * Internal ID returned in the previous look up response. Used for
	 * pagination.
	 */
	@Builder.Default
	@JsonProperty("last")
	String last = null;
}
