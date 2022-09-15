package cloud.pangeacyber.pangea.embargo;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class EmbargoSanctionAnnotationReference {
    @JsonProperty("paragraph")
    String paragraph;
    
    @JsonProperty("regulation")
    String regulation;

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getRegulation() {
        return regulation;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }
}
