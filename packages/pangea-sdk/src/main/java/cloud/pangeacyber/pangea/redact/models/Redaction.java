package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Redaction {

	@NonNull
	@JsonProperty("redaction_type")
	RedactType redactionType;

	@Builder.Default
	@JsonProperty("hash")
	Map<String, Object> hash = null;

	@Builder.Default
	@JsonProperty("fpe_alphabet")
	FPEAlphabet fpeAlphabet = null;

	@Builder.Default
	@JsonProperty("partial_masking")
	PartialMasking partialMasking = null;

	@Builder.Default
	@JsonProperty("redaction_value")
	String redactionValue = null;
}
