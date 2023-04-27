package cloud.pangeacyber.pangea.embargo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Vector;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class EmbargoSanctions {

	@JsonProperty("count")
	int count;

	@JsonProperty("sanctions")
	Vector<EmbargoSanction> sanctions;

	public int getCount() {
		return count;
	}

	public Vector<EmbargoSanction> getSanctions() {
		return sanctions;
	}
}
