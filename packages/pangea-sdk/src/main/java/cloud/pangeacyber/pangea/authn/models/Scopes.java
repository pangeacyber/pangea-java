package cloud.pangeacyber.pangea.authn.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Scopes extends ArrayList<String> {
}
