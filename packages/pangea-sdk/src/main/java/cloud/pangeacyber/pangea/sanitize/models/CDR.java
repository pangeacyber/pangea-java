package cloud.pangeacyber.pangea.sanitize.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CDR {

	@JsonProperty("file_attachments_removed")
	@JsonInclude(Include.NON_NULL)
	private Integer fileAttachmentsRemoved;

	@JsonProperty("interactive_contents_removed")
	@JsonInclude(Include.NON_NULL)
	private Integer interactiveContentsRemoved;

	public CDR() {}

	public Integer getFileAttachmentsRemoved() {
		return fileAttachmentsRemoved;
	}

	public Integer getInteractiveContentsRemoved() {
		return interactiveContentsRemoved;
	}
}
