package cloud.pangeacyber.pangea.redact.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonInclude(Include.NON_NULL)
@SuperBuilder
public class UnredactRequest<T> extends BaseRequest {

	/** Data to unredact */
	@NonNull
	@JsonProperty("redacted_data")
	T redactedData;

	/** FPE context used to decrypt and unredact data */
	@JsonProperty("fpe_context")
	String fpeContext;
}
