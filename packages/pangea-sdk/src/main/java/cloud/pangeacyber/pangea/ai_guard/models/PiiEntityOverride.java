package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PiiEntityOverride {

	@Builder.Default
	Boolean disabled = null;

	@Builder.Default
	PiiEntityAction emailAddress = null;

	@Builder.Default
	PiiEntityAction nrp = null;

	@Builder.Default
	PiiEntityAction location = null;

	@Builder.Default
	PiiEntityAction person = null;

	@Builder.Default
	PiiEntityAction phoneNumber = null;

	@Builder.Default
	PiiEntityAction dateTime = null;

	@Builder.Default
	PiiEntityAction ipAddress = null;

	@Builder.Default
	PiiEntityAction url = null;

	@Builder.Default
	PiiEntityAction money = null;

	@Builder.Default
	PiiEntityAction creditCard = null;

	@Builder.Default
	PiiEntityAction crypto = null;

	@Builder.Default
	PiiEntityAction ibanCode = null;

	@Builder.Default
	PiiEntityAction usBankNumber = null;

	@Builder.Default
	PiiEntityAction nif = null;

	@Builder.Default
	PiiEntityAction auAbn = null;

	@Builder.Default
	PiiEntityAction auAcn = null;

	@Builder.Default
	PiiEntityAction auTfn = null;

	@Builder.Default
	PiiEntityAction medicalLicense = null;

	@Builder.Default
	PiiEntityAction ukNhs = null;

	@Builder.Default
	PiiEntityAction auMedicare = null;

	@Builder.Default
	PiiEntityAction usDriversLicense = null;

	@Builder.Default
	PiiEntityAction usItin = null;

	@Builder.Default
	PiiEntityAction usPassport = null;

	@Builder.Default
	PiiEntityAction usSsn = null;
}
