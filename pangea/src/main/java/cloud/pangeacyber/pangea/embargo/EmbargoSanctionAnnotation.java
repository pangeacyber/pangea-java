package cloud.pangeacyber.pangea.embargo;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class EmbargoSanctionAnnotation {
    @JsonProperty("reference")
    EmbargoSanctionAnnotationReference reference;

    @JsonProperty("restriction_name")
    String restrictionName;

    public EmbargoSanctionAnnotationReference getReference() {
        return reference;
    }

    public void setReference(EmbargoSanctionAnnotationReference reference) {
        this.reference = reference;
    }

    public String getRestrictionName() {
        return restrictionName;
    }

    public void setRestrictionName(String restrictionName) {
        this.restrictionName = restrictionName;
    }
}
