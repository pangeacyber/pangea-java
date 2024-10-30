package cloud.pangeacyber.pangea.ai_guard.models;

import cloud.pangeacyber.pangea.intel.models.UserBreachedData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class TextGuardReport {

	IntelResults domainIntel;
	IntelResults ipIntel;
	RedactReport redact;
	IntelResults urlIntel;
	UserBreachedData userIntel;
}
