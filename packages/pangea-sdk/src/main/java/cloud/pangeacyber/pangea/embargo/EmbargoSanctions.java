package cloud.pangeacyber.pangea.embargo;

import java.util.Vector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
