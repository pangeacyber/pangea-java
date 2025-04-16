package cloud.pangeacyber.pangea.management.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
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

	@NonNull
	String scope;

	@NonNull
	AccessClientTokenAuth tokenEndpointAuthMethod;

	@NonNull
	List<String> redirectUris;

	@NonNull
	List<String> grantTypes;

	@NonNull
	List<String> responseTypes;

	Integer clientTokenExpiresIn;

	@NonNull
	String ownerId;

	@NonNull
	String ownerUsername;

	@NonNull
	String creatorId;

	@NonNull
	String clientClass;
}
