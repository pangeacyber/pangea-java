package cloud.pangeacyber.pangea.ai_guard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Value
public final class TopicResult {

	@JsonProperty("action")
	String action;

	@JsonProperty("topics")
	List<Topic> topics;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
	@Value
	public static class Topic {

		String topic;
		Float confidence;
	}
}
