package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SearchRestriction {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("actor")
    String[] actor;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("source")
    String[] source;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("target")
    String[] target;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("status")
    String[] status;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("action")
    String[] action;

    public SearchRestriction() {
    }

    public String[] getActor() {
        return actor;
    }

    public void setActor(String[] actor) {
        this.actor = actor;
    }

    public String[] getSource() {
        return source;
    }

    public void setSource(String[] source) {
        this.source = source;
    }

    public String[] getTarget() {
        return target;
    }

    public void setTarget(String[] target) {
        this.target = target;
    }

    public String[] getStatus() {
        return status;
    }

    public void setStatus(String[] status) {
        this.status = status;
    }

    public String[] getAction() {
        return action;
    }

    public void setAction(String[] action) {
        this.action = action;
    }
}
