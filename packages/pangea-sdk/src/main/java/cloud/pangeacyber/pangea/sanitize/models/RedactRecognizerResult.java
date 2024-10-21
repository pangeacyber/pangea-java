package cloud.pangeacyber.pangea.sanitize.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class RedactRecognizerResult {

	/** The entity name. */
	@JsonProperty("field_type")
	@JsonInclude(Include.NON_NULL)
	private String fieldType;

	/** The certainty score that the entity matches this specific snippet. */
	@JsonProperty("score")
	@JsonInclude(Include.NON_NULL)
	private Double score;

	/** The text snippet that matched. */
	@JsonProperty("text")
	@JsonInclude(Include.NON_NULL)
	private String text;

	/** The starting index of a snippet. */
	@JsonProperty("start")
	@JsonInclude(Include.NON_NULL)
	private Integer start;

	/** The ending index of a snippet. */
	@JsonProperty("end")
	@JsonInclude(Include.NON_NULL)
	private Integer end;

	/** Indicates if this rule was used to anonymize a text snippet. */
	@JsonProperty("redacted")
	@JsonInclude(Include.NON_NULL)
	private Boolean redacted;

	public RedactRecognizerResult() {}

	public String getFieldType() {
		return fieldType;
	}

	public Double getScore() {
		return score;
	}

	public String getText() {
		return text;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}

	public Boolean isRedacted() {
		return redacted;
	}
}
