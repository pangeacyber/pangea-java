package cloud.pangeacyber.pangea;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PangeaErrors {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("errors")
	ErrorField[] errors;

	public ErrorField[] getErrors() {
		return errors;
	}
}

final class ResponseError extends Response<PangeaErrors> {}
