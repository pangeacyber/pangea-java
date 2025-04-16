package cloud.pangeacyber.pangea.management.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class AccessClientSecretInfoWithMetadata {

	@NonNull
	String clientId;

	@NonNull
	String clientSecretId;

	@NonNull
	String clientSecretExpiresAt;

	@NonNull
	String clientSecretName;

	@NonNull
	String clientSecretDescription;

	@NonNull
	String createdAt;

	@NonNull
	String updatedAt;

	@NonNull
	AccessClientSecretMetadata clientSecretMetadata;
}
