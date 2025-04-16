package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class ServiceConfig {

	@NonNull
	String version;

	@NonNull
	String id;

	@NonNull
	String name;

	@NonNull
	String updatedAt;

	@NonNull
	List<String> enabledRules;

	/** Always run service config enabled rules across all redact calls regardless of flags? */
	Boolean enforceEnabledRules;

	Map<String, Redaction> redactions;

	/** Service config used to create the secret */
	String vaultServiceConfigId;

	/** Pangea only allows hashing to be done using a salt value to prevent brute-force attacks. */
	String saltVaultSecretId;

	/** The ID of the key used by FF3 Encryption algorithms for FPE. */
	String fpeVaultSecretId;

	Map<String, Rule> rules;
	Map<String, Ruleset> rulesets;
	List<String> supportedLanguages;
}
