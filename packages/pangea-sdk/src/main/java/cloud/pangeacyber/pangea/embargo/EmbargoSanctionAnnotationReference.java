package cloud.pangeacyber.pangea.embargo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class EmbargoSanctionAnnotationReference {
    @JsonProperty("paragraph")
    String paragraph;

    @JsonProperty("regulation")
    String regulation;

    public String getParagraph() {
        return paragraph;
    }

    public String getRegulation() {
        return regulation;
    }

}
