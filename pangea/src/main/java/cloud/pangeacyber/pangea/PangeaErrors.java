package cloud.pangeacyber.pangea;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PangeaErrors {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("errors")
    ErrorField[] errors;

    public ErrorField[] getErrors() {
        return errors;
    }
}

final class ResponseError extends Response<PangeaErrors> {};
