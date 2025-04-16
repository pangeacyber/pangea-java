package cloud.pangeacyber.pangea.management.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder(builderMethodName = "")
public class AccessClientInfo {

	@NonNull
	String clientId;

	@NonNull
	String createdAt;

	@NonNull
	String updatedAt;

	@NonNull
	String clientName;

	String scope;

	AccessClientTokenAuth tokenEndpointAuthMethod;

	List<String> redirectUris;

	List<String> grantTypes;

	List<String> responseTypes;

	@Builder.Default
	Integer clientTokenExpiresIn = null;

	String ownerId;

	String ownerUsername;

	String creatorId;

	String clientClass;
}
