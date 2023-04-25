package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import com.fasterxml.jackson.annotation.JsonProperty;

final class PasswordUpdateResquest {

	@JsonProperty("email")
	String email;

	@JsonProperty("old_secret")
	String oldSecret;

	@JsonProperty("new_secret")
	String newSecret;

	public PasswordUpdateResquest(String email, String oldSecret, String newSecret) {
		this.email = email;
		this.oldSecret = oldSecret;
		this.newSecret = newSecret;
	}
}

public class Password extends Client {

	public static final String serviceName = "authn";

	public Password(Config config) {
		super(config, serviceName);
	}
}
