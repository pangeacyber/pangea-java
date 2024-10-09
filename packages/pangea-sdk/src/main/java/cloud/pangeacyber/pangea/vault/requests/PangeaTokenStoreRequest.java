package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@Value
public class PangeaTokenStoreRequest extends CommonStoreRequest {

	@NonNull
	@JsonProperty("secret")
	String token;

	@NonNull
	@JsonProperty("type")
	static final String TYPE = "pangea_token";

	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod = null;
}
