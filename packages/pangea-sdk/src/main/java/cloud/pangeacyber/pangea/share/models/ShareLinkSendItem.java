package cloud.pangeacyber.pangea.share.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareLinkSendItem {

	@JsonProperty("id")
	private String id;

	@JsonProperty("email")
	private String email;

	public ShareLinkSendItem(String id, String email) {
		this.id = id;
		this.email = email;
	}

	public String getID() {
		return id;
	}

	public String getEmail() {
		return email;
	}
}
