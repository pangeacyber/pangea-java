package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class ItemVersionData {

	/** The item version. */
	@JsonProperty("version")
	int version;

	/** The state of the item version. */
	@JsonProperty("state")
	ItemVersionState state = null;

	/** Timestamp indicating when the item was created. */
	@JsonProperty("created_at")
	String createdAt = null;

	/** Timestamp indicating when the item version will be destroyed. */
	@JsonProperty("destroyed_at")
	String destroyedAt = null;

	@JsonProperty("public_key")
	String publicKey = null;

	@JsonProperty("secret")
	String secret = null;
}
