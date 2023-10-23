package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.FlowChoiceItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonFlowResult {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("flow_type")
	String[] flowType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disclaimer")
	String disclaimer;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("flow_phase")
	String flowPhase;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("flow_choices")
	FlowChoiceItem[] flowChoices;

	public String getFlowID() {
		return flowID;
	}

	public String[] getFlowType() {
		return flowType;
	}

	public String getEmail() {
		return email;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public String getFlowPhase() {
		return flowPhase;
	}

	public FlowChoiceItem[] getFlowChoices() {
		return flowChoices;
	}
}
