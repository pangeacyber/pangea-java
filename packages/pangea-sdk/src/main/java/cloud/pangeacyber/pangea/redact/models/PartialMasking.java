package cloud.pangeacyber.pangea.redact.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PartialMasking {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("masking_type")
	private MaskingType maskingType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("unmasked_from_left")
	private Integer unmaskedFromLeft;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("unmasked_from_right")
	private Integer unmaskedFromRight;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("masked_from_left")
	private Integer maskedFromLeft;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("masked_from_right")
	private Integer maskedFromRight;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("chars_to_ignore")
	private List<Character> charsToIgnore;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("masking_char")
	private List<Character> maskingChar;

	protected PartialMasking(Builder builder) {
		this.maskingType = builder.maskingType;
		this.unmaskedFromLeft = builder.unmaskedFromLeft;
		this.unmaskedFromRight = builder.unmaskedFromRight;
		this.maskedFromLeft = builder.maskedFromLeft;
		this.maskedFromRight = builder.maskedFromRight;
		this.charsToIgnore = builder.charsToIgnore;
		this.maskingChar = builder.maskingChar;
	}

	public MaskingType getMaskingType() {
		return maskingType;
	}

	public Integer getUnmaskedFromLeft() {
		return unmaskedFromLeft;
	}

	public Integer getUnmaskedFromRight() {
		return unmaskedFromRight;
	}

	public Integer getMaskedFromLeft() {
		return maskedFromLeft;
	}

	public Integer getMaskedFromRight() {
		return maskedFromRight;
	}

	public List<Character> getCharsToIgnore() {
		return charsToIgnore;
	}

	public List<Character> getMaskingChar() {
		return maskingChar;
	}

	public static class Builder {

		private MaskingType maskingType;
		private Integer unmaskedFromLeft;
		private Integer unmaskedFromRight;
		private Integer maskedFromLeft;
		private Integer maskedFromRight;
		private List<Character> charsToIgnore;
		private List<Character> maskingChar;

		public Builder() {}

		public PartialMasking build() {
			return new PartialMasking(this);
		}

		public Builder setMaskingType(MaskingType maskingType) {
			this.maskingType = maskingType;
			return this;
		}

		public Builder setUnmaskedFromLeft(Integer unmaskedFromLeft) {
			this.unmaskedFromLeft = unmaskedFromLeft;
			return this;
		}

		public Builder setUnmaskedFromRight(Integer unmaskedFromRight) {
			this.unmaskedFromRight = unmaskedFromRight;
			return this;
		}

		public Builder setMaskedFromLeft(Integer maskedFromLeft) {
			this.maskedFromLeft = maskedFromLeft;
			return this;
		}

		public Builder setMaskedFromRight(Integer maskedFromRight) {
			this.maskedFromRight = maskedFromRight;
			return this;
		}

		public Builder setCharsToIgnore(List<Character> charsToIgnore) {
			this.charsToIgnore = charsToIgnore;
			return this;
		}

		public Builder setMaskingChar(List<Character> maskingChar) {
			this.maskingChar = maskingChar;
			return this;
		}
	}
}
