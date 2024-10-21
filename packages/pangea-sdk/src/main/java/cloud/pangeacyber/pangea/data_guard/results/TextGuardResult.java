package cloud.pangeacyber.pangea.data_guard.results;

import cloud.pangeacyber.pangea.data_guard.models.TextGuardArtifact;
import cloud.pangeacyber.pangea.data_guard.models.TextGuardFindings;
import cloud.pangeacyber.pangea.data_guard.models.TextGuardReport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public final class TextGuardResult {

	List<TextGuardArtifact> artifacts;
	TextGuardFindings findings;
	String redactedPrompt;

	// `debug=true` only.
	TextGuardReport report;
}
