package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class RedactionMethodOverrides {

	@JsonProperty("redaction_type")
	private RedactType redactionType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("hash")
	private Map<String, Object> hash = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("fpe_alphabet")
	private FPEAlphabet fpeAlphabet = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("partial_masking")
	private PartialMasking partialMasking = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("redaction_value")
	private String redactionValue = null;

	protected RedactionMethodOverrides(Builder builder) {
		this.redactionType = builder.redactionType;
		this.hash = builder.hash;
		this.fpeAlphabet = builder.fpeAlphabet;
		this.partialMasking = builder.partialMasking;
		this.redactionValue = builder.redactionValue;
	}

	public RedactType getRedactionType() {
		return redactionType;
	}

	public Map<String, Object> getHash() {
		return hash;
	}

	public FPEAlphabet getFpeAlphabet() {
		return fpeAlphabet;
	}

	public PartialMasking getPartialMasking() {
		return partialMasking;
	}

	public String getRedactionValue() {
		return redactionValue;
	}

	public static class Builder {

		private RedactType redactionType;
		private Map<String, Object> hash = null;
		private FPEAlphabet fpeAlphabet = null;
		private PartialMasking partialMasking = null;
		private String redactionValue = null;

		public Builder(RedactType redactionType) {
			this.redactionType = redactionType;
		}

		public RedactionMethodOverrides build() {
			return new RedactionMethodOverrides(this);
		}

		public Builder setHash(Map<String, Object> hash) {
			this.hash = hash;
			return this;
		}

		public Builder setFpeAlphabet(FPEAlphabet fpeAlphabet) {
			this.fpeAlphabet = fpeAlphabet;
			return this;
		}

		public Builder setPartialMasking(PartialMasking partialMasking) {
			this.partialMasking = partialMasking;
			return this;
		}

		public Builder setRedactionValue(String redactionValue) {
			this.redactionValue = redactionValue;
			return this;
		}
	}
}
