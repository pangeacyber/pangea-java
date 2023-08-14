package cloud.pangeacyber.pangea;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorField {

	// """
	// Field errors denote errors in fields provided in request payloads

	// Fields:
	//     code(str): The field code
	//     detail(str): A human readable detail explaining the error
	//     source(str): A JSON pointer where the error occurred
	//     path(str): If verbose mode was enabled, a path to the JSON Schema used to validate the field
	// """

	@JsonProperty("code")
	String code;

	@JsonProperty("detail")
	String detail;

	@JsonProperty("source")
	String source;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("path")
	String path;

	public String getCode() {
		return code;
	}

	public String getDetail() {
		return detail;
	}

	public String getSource() {
		return source;
	}

	public String getPath() {
		return path;
	}

	public String toString() {
		return String.format("%s %s: %s.", this.source, this.code, this.detail);
	}
}
