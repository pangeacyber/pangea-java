package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainReputationBulkData extends HashMap<String, DomainReputationData> {}
