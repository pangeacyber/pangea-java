package cloud.pangeacyber.pangea.redact.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnredactResult<T> {

	@JsonProperty("data")
	T data;

	public T getData() {
		return data;
	}
}
