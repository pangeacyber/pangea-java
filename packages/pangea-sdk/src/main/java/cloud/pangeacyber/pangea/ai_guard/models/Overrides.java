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
public final class Overrides {

	/** Bypass existing Recipe content and create an on-the-fly Recipe. */
	@Builder.Default
	Boolean ignoreRecipe = null;

	@Builder.Default
	CodeDetectionOverride codeDetection = null;

	@Builder.Default
	CompetitorsOverride competitors = null;

	@Builder.Default
	GibberishOverride gibberish = null;

	@Builder.Default
	LanguageDetectionOverride languageDetection = null;

	@Builder.Default
	MaliciousEntityOverride maliciousEntity = null;

	@Builder.Default
	PiiEntityOverride piiEntity = null;

	@Builder.Default
	PromptInjectionOverride promptInjection = null;

	@Builder.Default
	RoleplayOverride roleplay = null;

	@Builder.Default
	SecretsDetectionOverride secretsDetection = null;

	@Builder.Default
	SelfHarmOverride selfharm = null;

	@Builder.Default
	SentimentOverride sentiment = null;

	@Builder.Default
	TopicDetectionOverride topicDetection = null;
}
