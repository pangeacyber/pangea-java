package cloud.pangeacyber.pangea.management.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.management.models.AccessClientTokenAuth;
import cloud.pangeacyber.pangea.management.models.AccessRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
public final class CreateClientRequest extends BaseRequest {

	/**
	 * The name of the client
	 */
	@NonNull
	String clientName;

	/**
	 * A list of space separated scope
	 */
	@NonNull
	String scope;

	/**
	 * The authentication method for the token endpoint
	 */
	AccessClientTokenAuth tokenEndpointAuthMethod;

	/**
	 * A list of allowed redirect URIs for the client
	 */
	List<String> redirectUris;

	/**
	 * A list of OAuth grant types that the client can use
	 */
	List<String> grantTypes;

	/**
	 * A list of OAuth response types that the client can use
	 */
	List<String> responseTypes;

	/**
	 * A positive time duration in seconds or null
	 */
	Integer clientSecretExpiresIn;

	/**
	 * A positive time duration in seconds or null
	 */
	Integer clientTokenExpiresIn;

	/**
	 * The name of the client secret
	 */
	String clientSecretName;

	/**
	 * The description of the client secret
	 */
	String clientSecretDescription;

	/**
	 * A list of roles
	 */
	List<AccessRole> roles;
}
