package cloud.pangeacyber.pangea.audit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** A description of acceptable fields for an audit log. */
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class AuditSchema {

	/** If true, records contain fields to support client/vault signing. */
	Boolean clientSignable;

	/** Save (or reject) malformed AuditEvents. */
	String saveMalformed;

	/** If true, records contain fields to support tamper-proofing. */
	Boolean tamperProofing;

	/** List of field definitions. */
	List<AuditSchemaField> fields;
}
