package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class ExtraInfo {

	/** Name of source application/agent. */
	String appName;

	/** The group of source application/agent. */
	String appGroup;

	/** Version of the source application/agent. */
	String appVersion;

	/** Name of subject actor/service account. */
	String actorName;

	/** The group of subject actor. */
	String actorGroup;

	/** Geographic region or data center. */
	String sourceRegion;

	/** Sub tenant of the user or organization */
	String subTenant;

	/** MCP tools grouped by server */
	List<McpServer> mcpTools;
}
