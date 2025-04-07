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
public class SecretsDetectionOverride {

	@Builder.Default
	Boolean disabled = null;

	@Builder.Default
	PiiEntityAction slackToken = null;

	@Builder.Default
	PiiEntityAction rsaPrivateKey = null;

	@Builder.Default
	PiiEntityAction sshDsaPrivateKey = null;

	@Builder.Default
	PiiEntityAction sshEcPrivateKey = null;

	@Builder.Default
	PiiEntityAction pgpPrivateKeyBlock = null;

	@Builder.Default
	PiiEntityAction amazonAwsAccessKeyId = null;

	@Builder.Default
	PiiEntityAction amazonAwsSecretAccessKey = null;

	@Builder.Default
	PiiEntityAction amazonMwsAuthToken = null;

	@Builder.Default
	PiiEntityAction facebookAccessToken = null;

	@Builder.Default
	PiiEntityAction githubAccessToken = null;

	@Builder.Default
	PiiEntityAction jwtToken = null;

	@Builder.Default
	PiiEntityAction googleApiKey = null;

	@Builder.Default
	PiiEntityAction googleCloudPlatformApiKey = null;

	@Builder.Default
	PiiEntityAction googleDriveApiKey = null;

	@Builder.Default
	PiiEntityAction googleCloudPlatformServiceAccount = null;

	@Builder.Default
	PiiEntityAction googleGmailApiKey = null;

	@Builder.Default
	PiiEntityAction youtubeApiKey = null;

	@Builder.Default
	PiiEntityAction mailchimpApiKey = null;

	@Builder.Default
	PiiEntityAction mailgunApiKey = null;

	@Builder.Default
	PiiEntityAction basicAuth = null;

	@Builder.Default
	PiiEntityAction picaticApiKey = null;

	@Builder.Default
	PiiEntityAction slackWebhook = null;

	@Builder.Default
	PiiEntityAction stripeApiKey = null;

	@Builder.Default
	PiiEntityAction stripeRestrictedApiKey = null;

	@Builder.Default
	PiiEntityAction squareAccessToken = null;

	@Builder.Default
	PiiEntityAction squareOauthSecret = null;

	@Builder.Default
	PiiEntityAction twilioApiKey = null;

	@Builder.Default
	PiiEntityAction pangeaToken = null;
}
