package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class URLReputationBulkData extends HashMap<String, URLReputationData> {}
