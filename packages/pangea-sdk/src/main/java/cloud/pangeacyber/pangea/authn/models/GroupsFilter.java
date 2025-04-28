package cloud.pangeacyber.pangea.authn.models;

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

/** Search filter for groups. */
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class GroupsFilter {

	/** Only records where created_at equals this value. */
	String createdAt;

	/** Only records where created_at is greater than this value. */
	@JsonProperty("created_at__gt")
	String createdAtGreaterThan;

	/** Only records where created_at is greater than or equal to this value. */
	@JsonProperty("created_at__gte")
	String createdAtGreaterThanOrEqual;

	/** Only records where created_at is less than this value. */
	@JsonProperty("created_at__lt")
	String createdAtLessThan;

	/** Only records where created_at is less than or equal to this value. */
	@JsonProperty("created_at__lte")
	String createdAtLessThanOrEqual;

	/** Only records where created_at includes this value. */
	@JsonProperty("created_at__contains")
	String createdAtContains;

	/** Only records where id equals this value. */
	String id;

	/** Only records where id includes each substring. */
	@JsonProperty("id__contains")
	List<String> idContains;

	/** Only records where id equals one of the provided substrings. */
	@JsonProperty("id__in")
	List<String> idIn;

	/** Only records where name equals this value. */
	String name;

	/** Only records where name includes each substring. */
	@JsonProperty("name__contains")
	List<String> nameContains;

	/** Only records where name equals one of the provided substrings. */
	@JsonProperty("name__in")
	List<String> nameIn;

	/** Only records where type equals this value. */
	String type;

	/** Only records where type includes each substring. */
	@JsonProperty("type__contains")
	List<String> typeContains;

	/** Only records where type equals one of the provided substrings. */
	@JsonProperty("type__in")
	List<String> typeIn;

	/** Only records where updated_at equals this value. */
	String updatedAt;

	/** Only records where updated_at is greater than this value. */
	@JsonProperty("updated_at__gt")
	String updatedAtGreaterThan;

	/** Only records where updated_at is greater than or equal to this value. */
	@JsonProperty("updated_at__gte")
	String updatedAtGreaterThanOrEqual;

	/** Only records where updated_at is less than this value. */
	@JsonProperty("updated_at__lt")
	String updatedAtLessThan;

	/** Only records where updated_at is less than or equal to this value. */
	@JsonProperty("updated_at__lte")
	String updatedAtLessThanOrEqual;

	/** Only records where updated_at includes this value. */
	@JsonProperty("updated_at__contains")
	String updatedAtContains;
}
