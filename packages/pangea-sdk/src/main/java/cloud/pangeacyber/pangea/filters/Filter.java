package cloud.pangeacyber.pangea.filters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter extends HashMap<String, Object> {}
