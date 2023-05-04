package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyPassword {

	@JsonProperty("password_chars_min")
	int charsMin;

	@JsonProperty("password_chars_max")
	int charsMax;

	@JsonProperty("password_lower_min")
	int lowerMin;

	@JsonProperty("password_upper_min")
	int upperMin;

	@JsonProperty("password_punct_min")
	int punctMin;

	@JsonProperty("password_number_min")
	int numberMin;

	public int getCharsMin() {
		return charsMin;
	}

	public int getCharsMax() {
		return charsMax;
	}

	public int getLowerMin() {
		return lowerMin;
	}

	public int getUpperMin() {
		return upperMin;
	}

	public int getPunctMin() {
		return punctMin;
	}

	public int getNumberMin() {
		return numberMin;
	}
}
