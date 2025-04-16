package cloud.pangeacyber.pangea.management.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.management.models.ListProjectsFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
public final class ListProjectsRequest extends BaseRequest {

	/** An Organization Pangea ID */
	@NonNull
	String orgId;

	/** Optional filter criteria */
	ListProjectsFilter filter;

	/** Optional offset for pagination */
	Integer offset;

	/** Optional limit for pagination */
	Integer limit;
}
