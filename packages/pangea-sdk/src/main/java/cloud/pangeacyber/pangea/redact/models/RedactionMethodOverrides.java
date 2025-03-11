package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RedactionMethodOverrides {

	@NonNull
	@JsonProperty("redaction_type")
	RedactType redactionType;

	@JsonProperty("hash")
	Map<String, Object> hash;

	@JsonProperty("fpe_alphabet")
	FPEAlphabet fpeAlphabet;

	@JsonProperty("partial_masking")
	PartialMasking partialMasking;

	@JsonProperty("redaction_value")
	String redactionValue;
}
