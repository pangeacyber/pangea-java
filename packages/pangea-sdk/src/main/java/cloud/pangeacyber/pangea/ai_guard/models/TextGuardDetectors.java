package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public final class TextGuardDetectors {

	TextGuardDetector<PromptInjectionResult> promptInjection;
	TextGuardDetector<PiiEntityResult> piiEntity;
	TextGuardDetector<MaliciousEntityResult> maliciousEntity;
	TextGuardDetector<SecretsEntityResult> secretsDetection;
	TextGuardDetector<Object> profanityAndToxicity;
	TextGuardDetector<Object> customEntity;
	TextGuardDetector<LanguageDetectionResult> languageDetection;
	TextGuardDetector<CodeDetectionResult> codeDetection;
}
