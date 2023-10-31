package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateResult extends User {}
