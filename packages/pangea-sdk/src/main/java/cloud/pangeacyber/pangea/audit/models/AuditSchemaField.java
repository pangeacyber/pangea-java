package cloud.pangeacyber.pangea.audit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** A description of a field in an audit log. */
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Value
public final class AuditSchemaField {

	/** Prefix name / identity for the field. */
	@NonNull
	String id;

	/** The data type for the field. */
	@NonNull
	String type;

	/** Human display description of the field. */
	String description;

	/** Human display name/title of the field. */
	String name;

	/** If true, redaction is performed against this field (if configured.) Only valid for string type. */
	Boolean redact;

	/** If true, this field is required to exist in all logged events. */
	Boolean required;

	/** The maximum size of the field. Only valid for strings, which limits number of UTF-8 characters. */
	Integer size;

	/** If true, this field is visible by default in audit UIs. */
	Boolean uiDefaultVisible;
}
