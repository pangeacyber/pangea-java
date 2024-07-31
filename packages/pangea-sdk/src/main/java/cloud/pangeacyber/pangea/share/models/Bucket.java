package cloud.pangeacyber.pangea.share.models;

import cloud.pangeacyber.pangea.TransferMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Bucket {

	/** If true, is the default bucket. */
	@JsonProperty("default")
	private Boolean default_;

	/** The ID of a share bucket resource. */
	@JsonProperty("id")
	private String id;

	/** The bucket's friendly name. */
	@JsonProperty("name")
	private String name;

	@JsonProperty("transfer_methods")
	private List<TransferMethod> transferMethods;

	public Bucket() {}
}
