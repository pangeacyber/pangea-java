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
public final class TextGuardDetectors {

	TextGuardDetector<PromptInjectionResult> promptInjection;
	TextGuardDetector<PiiEntityResult> piiEntity;
	TextGuardDetector<MaliciousEntityResult> maliciousEntity;
	TextGuardDetector<SecretsEntityResult> secretsDetection;
	TextGuardDetector<Object> profanityAndToxicity;
	TextGuardDetector<Object> customEntity;
	TextGuardDetector<LanguageDetectionResult> languageDetection;
	TextGuardDetector<TopicDetectionResult> topicDetection;
	TextGuardDetector<CodeDetectionResult> codeDetection;
}
