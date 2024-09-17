package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
public class GetRequest extends BaseRequest {

	/** The item ID. */
	@NonNull
	@JsonProperty("id")
	String id;

	/** The key version(s). */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	String version;
}
