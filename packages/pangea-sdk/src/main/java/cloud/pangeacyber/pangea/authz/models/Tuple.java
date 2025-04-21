package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.Instant;
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
public final class Tuple {

	@NonNull
	private Resource resource;

	@NonNull
	private String relation;

	@NonNull
	private Subject subject;

	@Builder.Default
	private Instant expiresAt = null;

	public Tuple(Resource resource, String relation, Subject subject) {
		this(resource, relation, subject, null);
	}

	public Tuple(Resource resource, String relation, Subject subject, Instant expiresAt) {
		this.resource = resource;
		this.relation = relation;
		this.subject = subject;
		this.expiresAt = expiresAt;
	}
}
