package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollMFAStart {

	@JsonProperty("mfa_providers")
	MFAProviders mfaProviders;

	public MFAProviders getMfaProviders() {
		return mfaProviders;
	}
}
