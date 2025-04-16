package cloud.pangeacyber.pangea.management.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
@Value
public final class ListClientRolesRequest extends BaseRequest {

	/** Filter by resource type */
	String resourceType;

	/** Filter by resource ID */
	String resourceId;

	/** Filter by role */
	String role;
}
