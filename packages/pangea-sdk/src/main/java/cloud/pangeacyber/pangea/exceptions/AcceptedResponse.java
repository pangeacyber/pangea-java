package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcceptedResponse extends Response<AcceptedResult> {}
