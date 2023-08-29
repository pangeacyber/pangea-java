package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.AgreementInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementUpdateResult extends AgreementInfo {}
