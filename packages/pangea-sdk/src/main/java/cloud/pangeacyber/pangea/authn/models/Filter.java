package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter extends HashMap<String, Object> {}
