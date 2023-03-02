package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JWK {

	@JsonProperty("alg")
	String alg;

	@JsonProperty("kty")
	String kty;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("kid")
	String kid;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("use")
	String use;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("crv")
	String crv;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("d")
	String d;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("x")
	String x;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("y")
	String y;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("n")
	String n;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("e")
	String e;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("k")
	String k;

	public JWK() {}

	public String getAlg() {
		return alg;
	}

	public String getKty() {
		return kty;
	}

	public String getKid() {
		return kid;
	}

	public String getUse() {
		return use;
	}

	public String getCrv() {
		return crv;
	}

	public String getD() {
		return d;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public String getN() {
		return n;
	}

	public String getE() {
		return e;
	}

	public String getK() {
		return k;
	}
}
