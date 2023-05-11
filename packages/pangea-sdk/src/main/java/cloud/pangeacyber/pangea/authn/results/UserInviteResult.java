package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.UserInvite;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInviteResult extends UserInvite {}
