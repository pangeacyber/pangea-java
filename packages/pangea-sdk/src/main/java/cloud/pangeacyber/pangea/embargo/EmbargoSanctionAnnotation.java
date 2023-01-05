package cloud.pangeacyber.pangea.embargo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class EmbargoSanctionAnnotation {
    @JsonProperty("reference")
    EmbargoSanctionAnnotationReference reference;

    @JsonProperty("restriction_name")
    String restrictionName;

    public EmbargoSanctionAnnotationReference getReference() {
        return reference;
    }

    public String getRestrictionName() {
        return restrictionName;
    }

}
