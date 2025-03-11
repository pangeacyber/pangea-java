package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value
public class RedactRecognizerResult {

	/** The entity name */
	@JsonProperty("field_type")
	String fieldType;

	/** The certainty score that the entity matches this specific snippet */
	@JsonProperty("score")
	int score;

	/** The text snippet that matched */
	@JsonProperty("text")
	String text;

	/** The starting index of a snippet */
	@JsonProperty("start")
	int start;

	/** The ending index of a snippet */
	@JsonProperty("end")
	int end;

	/** Indicates if this rule was used to anonymize a text snippet */
	@JsonProperty("redacted")
	boolean redacted;

	/**
	 * If this result relates to a specific structured text field, the key
	 * pointing to this text will be provided
	 */
	@JsonProperty("data_key")
	String dataKey;
}
