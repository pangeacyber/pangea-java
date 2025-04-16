package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Filter criteria for service config queries. */
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class ServiceConfigFilter {

	/** Only records where id equals this value. */
	@JsonProperty("id")
	String id;

	/** Only records where id includes each substring. */
	@JsonProperty("id__contains")
	List<String> idContains;

	/** Only records where id equals one of the provided substrings. */
	@JsonProperty("id__in")
	List<String> idIn;

	/** Only records where created_at equals this value. */
	@JsonProperty("created_at")
	String createdAt;

	/** Only records where created_at is greater than this value. */
	@JsonProperty("created_at__gt")
	String createdAtGt;

	/** Only records where created_at is greater than or equal to this value. */
	@JsonProperty("created_at__gte")
	String createdAtGte;

	/** Only records where created_at is less than this value. */
	@JsonProperty("created_at__lt")
	String createdAtLt;

	/** Only records where created_at is less than or equal to this value. */
	@JsonProperty("created_at__lte")
	String createdAtLte;

	/** Only records where updated_at equals this value. */
	@JsonProperty("updated_at")
	String updatedAt;

	/** Only records where updated_at is greater than this value. */
	@JsonProperty("updated_at__gt")
	String updatedAtGt;

	/** Only records where updated_at is greater than or equal to this value. */
	@JsonProperty("updated_at__gte")
	String updatedAtGte;

	/** Only records where updated_at is less than this value. */
	@JsonProperty("updated_at__lt")
	String updatedAtLt;

	/** Only records where updated_at is less than or equal to this value. */
	@JsonProperty("updated_at__lte")
	String updatedAtLte;
}
