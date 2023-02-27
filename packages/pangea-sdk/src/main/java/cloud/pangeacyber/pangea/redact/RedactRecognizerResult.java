package cloud.pangeacyber.pangea.redact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedactRecognizerResult {

	@JsonProperty("field_type")
	String fieldType;

	@JsonProperty("score")
	int score;

	@JsonProperty("text")
	String text;

	@JsonProperty("start")
	int start;

	@JsonProperty("end")
	int end;

	@JsonProperty("redacted")
	boolean redacted;

	@JsonProperty("data_key")
	String dataKey;

	public String getFieldType() {
		return fieldType;
	}

	public int getScore() {
		return score;
	}

	public String getText() {
		return text;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public boolean isRedacted() {
		return redacted;
	}

	public String getDataKey() {
		return dataKey;
	}
}
