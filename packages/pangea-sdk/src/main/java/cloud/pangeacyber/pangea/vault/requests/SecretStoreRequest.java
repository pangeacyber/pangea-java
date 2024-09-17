package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@Value
public class SecretStoreRequest extends CommonStoreRequest {

	@NonNull
	@JsonProperty("secret")
	String secret;

	@JsonProperty("type")
	String type = "secret";
}
