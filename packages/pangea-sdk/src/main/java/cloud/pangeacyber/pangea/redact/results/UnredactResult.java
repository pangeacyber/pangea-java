package cloud.pangeacyber.pangea.redact.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value
public class UnredactResult<T> {

	/** The unredacted data */
	@JsonProperty("data")
	T data;
}
