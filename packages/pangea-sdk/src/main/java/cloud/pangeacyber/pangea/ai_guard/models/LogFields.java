package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class LogFields {

	/**
	 * Origin or source application of the event. Examples: 'Slack', 'GDrive',
	 * 'Confluence', 'Workday'.
	 */
	String citations;

	/** Stores supplementary details related to the event. */
	String extraInfo;

	/** Model used to perform the event. Example: '{model: gpt, version: 3.5}'. */
	String model;

	/** IP address of user or app or agent. */
	String source;

	/** Tools used to perform the event. Example: '{tool:ai-guard}'. */
	String tools;
}
