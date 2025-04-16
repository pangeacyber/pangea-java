package cloud.pangeacyber.pangea.management.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
public final class ListClientSecretMetadataRequest extends BaseRequest {

	/**
	 * The client ID to list secrets for
	 */
	@NonNull
	String id;

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
	 * Only records where name equals this value
	 */
	@JsonProperty("client_secret_name")
	String clientSecretName;

	/**
	 * Only records where name includes each substring
	 */
	@JsonProperty("client_secret_name__contains")
	List<String> clientSecretNameContains;

	/**
	 * Only records where name equals one of the provided substrings
	 */
	@JsonProperty("client_secret_name__in")
	List<String> clientSecretNameIn;

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
	@JsonProperty("size")
	Integer size;
}
