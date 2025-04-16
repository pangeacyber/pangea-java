package cloud.pangeacyber.pangea.audit.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.audit.models.ServiceConfigFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class ListServiceConfigsRequest extends BaseRequest {

	/** Filter criteria for service config queries. */
	ServiceConfigFilter filter;

	/** Reflected value from a previous response to obtain the next page of results. */
	String last;

	/** Order results asc(ending) or desc(ending). */
	String order;

	/** Which field to order results by. */
	String orderBy;

	/** Maximum results to include in the response. */
	@Builder.Default
	Integer size = null;
}
