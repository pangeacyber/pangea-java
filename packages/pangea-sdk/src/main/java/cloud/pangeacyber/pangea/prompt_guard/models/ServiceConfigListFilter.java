package cloud.pangeacyber.pangea.prompt_guard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class ServiceConfigListFilter {

	String id;

	@JsonProperty("id__contains")
	List<String> idContains;

	@JsonProperty("id__in")
	List<String> idIn;

	@JsonProperty("created_at")
	OffsetDateTime createdAt;

	@JsonProperty("created_at__gt")
	OffsetDateTime createdAtGt;

	@JsonProperty("created_at__gte")
	OffsetDateTime createdAtGte;

	@JsonProperty("created_at__lt")
	OffsetDateTime createdAtLt;

	@JsonProperty("created_at__lte")
	OffsetDateTime createdAtLte;

	@JsonProperty("updated_at")
	OffsetDateTime updatedAt;

	@JsonProperty("updated_at__gt")
	OffsetDateTime updatedAtGt;

	@JsonProperty("updated_at__gte")
	OffsetDateTime updatedAtGte;

	@JsonProperty("updated_at__lt")
	OffsetDateTime updatedAtLt;

	@JsonProperty("updated_at__lte")
	OffsetDateTime updatedAtLte;
}
