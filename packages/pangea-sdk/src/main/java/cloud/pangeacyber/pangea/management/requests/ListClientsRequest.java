package cloud.pangeacyber.pangea.management.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
@Value
public final class ListClientsRequest extends BaseRequest {

	/**
	 * Only records where created_at equals this value
	 */
	@JsonProperty("created_at")
	String createdAt;

	/**
	 * Only records where created_at is greater than this value
	 */
	@JsonProperty("created_at__gt")
	String createdAtGt;

	/**
	 * Only records where created_at is greater than or equal to this value
	 */
	@JsonProperty("created_at__gte")
	String createdAtGte;

	/**
	 * Only records where created_at is less than this value
	 */
	@JsonProperty("created_at__lt")
	String createdAtLt;

	/**
	 * Only records where created_at is less than or equal to this value
	 */
	@JsonProperty("created_at__lte")
	String createdAtLte;

	/**
	 * Only records where id equals this value
	 */
	@JsonProperty("client_id")
	String clientId;

	/**
	 * Only records where id includes each substring
	 */
	@JsonProperty("client_id__contains")
	List<String> clientIdContains;

	/**
	 * Only records where id equals one of the provided substrings
	 */
	@JsonProperty("client_id__in")
	List<String> clientIdIn;

	/**
	 * Only records where name equals this value
	 */
	@JsonProperty("client_name")
	String clientName;

	/**
	 * Only records where name includes each substring
	 */
	@JsonProperty("client_name__contains")
	List<String> clientNameContains;

	/**
	 * Only records where name equals one of the provided substrings
	 */
	@JsonProperty("client_name__in")
	List<String> clientNameIn;

	/**
	 * A list of tags that all must be present
	 */
	@JsonProperty("scopes")
	List<String> scopes;

	/**
	 * Only records where updated_at equals this value
	 */
	@JsonProperty("updated_at")
	String updatedAt;

	/**
	 * Only records where updated_at is greater than this value
	 */
	@JsonProperty("updated_at__gt")
	String updatedAtGt;

	/**
	 * Only records where updated_at is greater than or equal to this value
	 */
	@JsonProperty("updated_at__gte")
	String updatedAtGte;

	/**
	 * Only records where updated_at is less than this value
	 */
	@JsonProperty("updated_at__lt")
	String updatedAtLt;

	/**
	 * Only records where updated_at is less than or equal to this value
	 */
	@JsonProperty("updated_at__lte")
	String updatedAtLte;

	/**
	 * Reflected value from a previous response to obtain the next page of results
	 */
	@JsonProperty("last")
	String last;

	/**
	 * Order results asc(ending) or desc(ending)
	 */
	@JsonProperty("order")
	String order;

	/**
	 * Which field to order results by
	 */
	@JsonProperty("order_by")
	String orderBy;

	/**
	 * Maximum results to include in the response
	 */
	@Builder.Default
	Integer size = null;
}
