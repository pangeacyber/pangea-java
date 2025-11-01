package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value
public final class GuardDetectors {

	GuardDetector<PromptInjectionResult> maliciousPrompt;
	GuardDetector<RedactEntityResult> confidentialAndPiiEntity;
	GuardDetector<MaliciousEntityResult> maliciousEntity;
	GuardDetector<RedactEntityResult> customEntity;
	GuardDetector<RedactEntityResult> secretAndKeyEntity;
	GuardDetector<LanguageDetectionResult> language;
	GuardDetector<LanguageDetectionResult> code;
}
